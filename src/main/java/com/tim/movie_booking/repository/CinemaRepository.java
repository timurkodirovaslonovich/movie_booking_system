package com.tim.movie_booking.repository;

import com.tim.movie_booking.dto.CinemaResponseDto;
import com.tim.movie_booking.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CinemaRepository extends JpaRepository<Cinema, UUID> {
//    CinemaResponseDto findByName(String name);
    boolean existsByName(String name);
}
