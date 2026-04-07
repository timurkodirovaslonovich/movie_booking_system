package com.tim.movie_booking.service;

import com.tim.movie_booking.dto.MovieRequestDto;
import com.tim.movie_booking.dto.MovieResponseDto;
import com.tim.movie_booking.entity.User;

import java.util.List;
import java.util.UUID;

public interface MovieService {
    List<MovieResponseDto> getAllMovies(); //only Admin
    MovieResponseDto createMovie(User currentUser, MovieRequestDto request); //only admin
    MovieResponseDto getMovieById(UUID uuid);
}
