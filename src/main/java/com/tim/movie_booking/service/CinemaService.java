package com.tim.movie_booking.service;

import com.tim.movie_booking.dto.CinemaRequestDto;
import com.tim.movie_booking.dto.CinemaResponseDto;

import java.util.List;
import java.util.UUID;

public interface CinemaService {
    CinemaResponseDto getCinemaById(UUID uuid);
    List<CinemaResponseDto> getCinemas();
    CinemaResponseDto createCinema(CinemaRequestDto request);
    CinemaResponseDto updateCinema(CinemaRequestDto request, UUID uuid);
    void deleteCinema(UUID uuid);
}
