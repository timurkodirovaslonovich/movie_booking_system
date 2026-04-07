package com.tim.movie_booking.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class HallResponseDto {
    private UUID id;
    private String hallNumber;   // ✅ String — matches entity
    private Integer capacity;
    private UUID cinemaId;
    private String cinemaName;
}