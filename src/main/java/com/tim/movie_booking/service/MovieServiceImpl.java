package com.tim.movie_booking.service;


import com.tim.movie_booking.dto.HallResponseDto;
import com.tim.movie_booking.dto.MovieRequestDto;
import com.tim.movie_booking.dto.MovieResponseDto;
import com.tim.movie_booking.dto.ShowTimesResponseDto;
import com.tim.movie_booking.entity.*;
import com.tim.movie_booking.exception.ResourceNotFoundException;
import com.tim.movie_booking.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;




    @Override
    public List<MovieResponseDto> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public MovieResponseDto createMovie(User currentUser, MovieRequestDto request) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new ResourceNotFoundException("You are not admin");
        }

        if (request.getMovieName() == null) {
            throw new IllegalArgumentException("Movie name can not be empty");
        }

        if (request.getDescription() == null){
            throw new IllegalArgumentException("Movie description can not be empty");
        }

        Movie movie = new Movie();
        movie.setMovieName(request.getMovieName());
        movie.setDescription(request.getDescription());

        Movie savedMovie = movieRepository.save(movie);

        return toDto(savedMovie);
    }

    @Override
    public MovieResponseDto getMovieById(UUID uuid) {
        Movie foundMovie = movieRepository.findById(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Movie not found with id: " + uuid));

        return toDto(foundMovie);
    }

    //Mapper
    public MovieResponseDto toDto(Movie movie) {
        MovieResponseDto dto = new MovieResponseDto();
        dto.setUuid(movie.getId());
        dto.setMovieName(movie.getMovieName());
        dto.setDescription(movie.getDescription());

        List<ShowTimesResponseDto> showTimeDtos = movie.getShowTimes()
                .stream()
                .map(this::toShowTimeDto)
                .toList();

        dto.setShowTimes(showTimeDtos);
        return dto;
    }

    private ShowTimesResponseDto toShowTimeDto(ShowTime showTime) {
        ShowTimesResponseDto dto = new ShowTimesResponseDto();
        dto.setUuid(showTime.getId());
        dto.setStartTime(showTime.getStartTime());
        dto.setPrice(showTime.getPrice());

        // ✅ availableSeats — count seats in the hall that are still AVAILABLE
        long availableSeats = showTime.getHall().getSeats()
                .stream()
                .filter(seat -> seat.getSeatStatus() == SeatStatus.AVAILABLE)
                .count();
        dto.setAvailableSeats((int) availableSeats);

        // ✅ Nested hall DTO
        HallResponseDto hallDto = new HallResponseDto();
        hallDto.setId(showTime.getHall().getId());
        hallDto.setHallNumber(showTime.getHall().getHallNumber());
        hallDto.setCapacity(showTime.getHall().getCapacity());
        dto.setHall(hallDto);

        // ✅ Notice: no movie field here — we're already inside a movie, would cause infinite loop
        // movie → showTimes → movie → showTimes → ...
        dto.setMovie(null);

        return dto;
    }
}
