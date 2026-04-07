package com.tim.movie_booking.repository;

import com.tim.movie_booking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {



}
