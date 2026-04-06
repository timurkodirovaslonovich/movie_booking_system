// repository/BookingRepository.java
package com.tim.movie_booking.repository;

import com.tim.movie_booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByUserId(UUID userId);
    List<Booking> findByShowTimeId(UUID showTimeId);
}