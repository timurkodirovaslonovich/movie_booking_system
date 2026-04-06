package com.tim.movie_booking.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ShowTimesResponseDto {
    private UUID uuid;
    private MovieResponseDto movie;
    private HallResponseDto hall;
    private LocalDateTime startTime;
    private BigDecimal price;
    private Integer availableSeats;
}
