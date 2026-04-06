package com.tim.movie_booking.dto;

import com.tim.movie_booking.entity.SeatStatus;
import com.tim.movie_booking.entity.SeatType;
import lombok.Data;

import java.util.UUID;

@Data
public class SeatResponseDto {

    private UUID id;
    private Integer rowNumber;
    private Integer seatNumber;
    private SeatType seatType;
    private SeatStatus status;
    private UUID hallId;
}