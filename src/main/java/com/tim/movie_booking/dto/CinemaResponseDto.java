package com.tim.movie_booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;


@Data
@Getter
@Setter
public class CinemaResponseDto {
    private UUID uuid;
    private String name;
    private String address;
    private List<HallResponseDto> halls;
}
