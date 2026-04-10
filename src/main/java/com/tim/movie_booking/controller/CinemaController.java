package com.tim.movie_booking.controller;


import com.tim.movie_booking.service.CinemaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cinemas")
@RequiredArgsConstructor
@NoArgsConstructor
@Tag(name = "Cinema", description = "cinema public APIs")
public class CinemaController {

    private final CinemaService cinemaService;
}
