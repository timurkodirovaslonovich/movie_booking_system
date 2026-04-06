package com.tim.movie_booking.dto;

import com.tim.movie_booking.entity.BookingStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class BookingResponseDto {

    private UUID id;
    private UUID userId;
    private UUID showTimeId;
    private BookingStatus bookingStatus;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private List<TicketResponseDto> tickets;
}