package com.tim.movie_booking.dto;


import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MovieResponseDto {
    private UUID uuid;
    private String movieName;
    private String description;
    private List<ShowTimesResponseDto> showTimes;
}
