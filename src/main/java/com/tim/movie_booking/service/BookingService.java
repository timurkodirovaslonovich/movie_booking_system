package com.tim.movie_booking.service;

import com.tim.movie_booking.dto.BookingRequestDto;
import com.tim.movie_booking.dto.BookingResponseDto;
import com.tim.movie_booking.entity.User;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    BookingResponseDto createBooking(User currentUser, BookingRequestDto request);
    BookingResponseDto getBookingById(UUID uuid, User currentUser);
    List<BookingResponseDto> getMyBookings(User currentUser);
    BookingResponseDto cancelBooking(UUID uuid, User currentUser);
    List<BookingResponseDto> getAllBookings(); // only admin can
}
