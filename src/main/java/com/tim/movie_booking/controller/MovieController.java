package com.tim.movie_booking.controller;


import com.tim.movie_booking.dto.MovieResponseDto;
import com.tim.movie_booking.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@Tag(name = "Movie", description = "movie APIs")
public class MovieController {

    private final MovieService movieService;

    //admin
//    @GetMapping("/admin/all")
//    @PreAuthorize("hasRole('ADMIN')")
//    @Operation(summary = "get all bookings")
//    public ResponseEntity<List<MovieResponseDto>> getAllMovies() {
//        return ResponseEntity.ok(movieService.getAllMovies());
//    }





    //no login required
    @GetMapping
    @Operation(summary = "GET all movies(public)")
    public ResponseEntity<List<MovieResponseDto>> getAllMoviesPublic() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    //no login required
    @GetMapping("/{id}")
    @Operation(summary = "GET movie by ID")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(movieService.getMovieById(uuid));
    }


}
