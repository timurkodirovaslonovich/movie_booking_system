package com.tim.movie_booking.dto;

import com.tim.movie_booking.entity.TicketStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TicketResponseDto {

    private UUID id;
    private UUID bookingId;
    private UUID showTimeId;
    private SeatResponseDto seat;    // nested — shows row, number, type
    private TicketStatus ticketStatus;
    private BigDecimal price;
}