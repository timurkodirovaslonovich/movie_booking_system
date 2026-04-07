package com.tim.movie_booking.service;

import com.tim.movie_booking.dto.BookingRequestDto;
import com.tim.movie_booking.dto.BookingResponseDto;
import com.tim.movie_booking.dto.SeatResponseDto;
import com.tim.movie_booking.dto.TicketResponseDto;
import com.tim.movie_booking.entity.*;
import com.tim.movie_booking.exception.ResourceNotFoundException;
import com.tim.movie_booking.repository.BookingRepository;
import com.tim.movie_booking.repository.SeatRepository;
import com.tim.movie_booking.repository.ShowTimeRepository;
import com.tim.movie_booking.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final ShowTimeRepository showTimeRepository;
    private final SeatRepository seatRepository;

    @Override
    public BookingResponseDto createBooking(User currentUser, BookingRequestDto request) {

        //loading show time
        ShowTime showTime = showTimeRepository.findById(request.getShowTimeId()).orElseThrow(() ->
            new ResourceNotFoundException("Show time not found with id: " + request.getShowTimeId())
        );

        //loading the seats and validating them
        List<Seat> seats = new ArrayList<>();
        for (UUID seatId: request.getSeatIds()) {


            Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new ResourceNotFoundException(
               "Seat not found with id: " + seatId
            ));

            // 3 — Check seat belongs to the showtime's hall
            if (!seat.getHall().getId().equals(showTime.getHall().getId())) {
                throw new IllegalArgumentException(
                        "Seat " + seatId + " does not belong to this showtime's hall");
            }

            // 4 — Check seat is available
            if (seat.getSeatStatus() == SeatStatus.UNAVAILABLE) {
                throw new IllegalArgumentException(
                        "Seat " + seat.getRowNumber() + "-" + seat.getSeatNumber() + " is already taken");
            }

            seats.add(seat);

        }

        // 5 — Calculate total price
        BigDecimal totalPrice = showTime.getPrice()
                .multiply(BigDecimal.valueOf(seats.size()));

        // 6 — Create the booking
        Booking booking = new Booking();
        booking.setUser(currentUser);
        booking.setShowTime(showTime);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setTotalPrice(totalPrice);
        Booking savedBooking = bookingRepository.save(booking);

        // 7 — Create one ticket per seat + mark seat as unavailable
        List<Ticket> tickets = new ArrayList<>();
        for (Seat seat : seats) {
            Ticket ticket = new Ticket();
            ticket.setBooking(savedBooking);
            ticket.setShowTime(showTime);
            ticket.setSeat(seat);
            ticket.setTicketStatus(TicketStatus.BOOKED);
            ticket.setPrice(showTime.getPrice());
            tickets.add(ticket);

            // Mark seat as taken
            seat.setSeatStatus(SeatStatus.UNAVAILABLE);
            seatRepository.save(seat);
        }
        ticketRepository.saveAll(tickets);
        savedBooking.setTickets(tickets);

        return toDto(savedBooking);


    }

    @Override
    public BookingResponseDto getBookingById(UUID id, User currentUser) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        // Users can only see their own bookings — admins can see all
        if (!booking.getUser().getId().equals(currentUser.getId())
                && currentUser.getRole() != Role.ADMIN) {
            throw new ResourceNotFoundException("Booking not found with id: " + id);
        }

        return toDto(booking);
    }

    @Override
    public List<BookingResponseDto> getMyBookings(User currentUser) {
        return bookingRepository.findByUserId(currentUser.getId())
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional
    public BookingResponseDto cancelBooking(UUID id, User currentUser) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        // Only owner or admin can cancel
        if (!booking.getUser().getId().equals(currentUser.getId())
                && currentUser.getRole() != Role.ADMIN) {
            throw new ResourceNotFoundException("Booking not found with id: " + id);
        }

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Booking is already cancelled");
        }

        // Cancel booking and all its tickets
        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.getTickets().forEach(ticket -> {
            ticket.setTicketStatus(TicketStatus.CANCELLED);

            // Free up the seats again
            ticket.getSeat().setSeatStatus(SeatStatus.AVAILABLE);
            seatRepository.save(ticket.getSeat());
        });

        ticketRepository.saveAll(booking.getTickets());
        return toDto(bookingRepository.save(booking));
    }


    @Override
    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    // ─── Mappers ────────────────────────────────────────────

    private BookingResponseDto toDto(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUser().getId());
        dto.setShowTimeId(booking.getShowTime().getId());
        dto.setBookingStatus(booking.getBookingStatus());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setTickets(booking.getTickets().stream().map(this::toTicketDto).toList());
        return dto;
    }

    private TicketResponseDto toTicketDto(Ticket ticket) {
        TicketResponseDto dto = new TicketResponseDto();
        dto.setId(ticket.getId());
        dto.setBookingId(ticket.getBooking().getId());
        dto.setShowTimeId(ticket.getShowTime().getId());
        dto.setSeat(toSeatDto(ticket.getSeat()));
        dto.setTicketStatus(ticket.getTicketStatus());
        dto.setPrice(ticket.getPrice());
        return dto;
    }

    private SeatResponseDto toSeatDto(Seat seat) {
        SeatResponseDto dto = new SeatResponseDto();
        dto.setId(seat.getId());
        dto.setRowNumber(seat.getRowNumber());
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setSeatType(seat.getSeatType());
        dto.setStatus(seat.getSeatStatus());
        dto.setHallId(seat.getHall().getId());
        return dto;
    }
}
