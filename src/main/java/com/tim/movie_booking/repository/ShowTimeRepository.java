// repository/ShowTimeRepository.java
package com.tim.movie_booking.repository;

import com.tim.movie_booking.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShowTimeRepository extends JpaRepository<ShowTime, UUID> {
    List<ShowTime> findByMovieId(UUID movieId);
}