package com.tim.movie_booking.repository;

import com.tim.movie_booking.entity.Seat;
import com.tim.movie_booking.entity.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
    List<Seat> findByHallId(UUID hallId);
    List<Seat> findByHallIdAndSeatStatus(UUID hallId, SeatStatus seatStatus);
}
