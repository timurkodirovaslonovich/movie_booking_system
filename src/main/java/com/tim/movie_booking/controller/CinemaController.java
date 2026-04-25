package com.tim.movie_booking.controller;


import com.tim.movie_booking.dto.CinemaResponseDto;
import com.tim.movie_booking.service.CinemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cinemas")
@RequiredArgsConstructor
@Tag(name = "Cinema", description = "cinema public APIs")
public class CinemaController {

    private final CinemaService cinemaService;

    @GetMapping
    @Operation(summary = "getting cinemas(PUBLIC)")
    ResponseEntity<List<CinemaResponseDto>> getAllCinemas() {
        var cinemas = cinemaService.getCinemas();
        return ResponseEntity.ok(cinemas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Getting cinema by uuid")
    ResponseEntity<CinemaResponseDto> getCinemaById(@PathVariable UUID id) {
        CinemaResponseDto cinema = cinemaService.getCinemaById(id);
        return ResponseEntity.ok(cinema);
    }




}
