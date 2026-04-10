package com.tim.movie_booking.service;

import com.tim.movie_booking.dto.CinemaRequestDto;
import com.tim.movie_booking.dto.CinemaResponseDto;
import com.tim.movie_booking.dto.HallResponseDto;
import com.tim.movie_booking.entity.Cinema;
import com.tim.movie_booking.entity.Movie;
import com.tim.movie_booking.exception.ResourceNotFoundException;
import com.tim.movie_booking.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;



    @Override
    public CinemaResponseDto getCinemaById(UUID uuid) {
        Cinema foundCinema = cinemaRepository.findById(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Movie not found with id: " + uuid));

    }

    @Override
    public List<CinemaResponseDto> getCinemas() {
        return List.of();
    }

    @Override
    public CinemaResponseDto createCinema(CinemaRequestDto request) {
        return null;
    }

    @Override
    public CinemaResponseDto updateCinema(CinemaRequestDto request) {
        return null;
    }

    @Override
    public void deleteCinema(UUID uuid) {

    }


    //mapper methods
    public CinemaResponseDto toDto(Cinema cinema) {
        CinemaResponseDto dto = new CinemaResponseDto();
        dto.setUuid(cinema.getId());
        dto.setName(cinema.getName());
        dto.setAddress(cinema.getAddress());
        HallResponseDto hallDto = new HallResponseDto();
        hallDto.setCinemaId(cinema.getId());
        hallDto.setCapacity(hallDto.getCapacity());
        hallDto.setHallNumber(hallDto.getHallNumber());
        hallDto.setCinemaName(cinema.getName());
        dto.setHalls(List.of(hallDto));

        return dto;
    }
}
