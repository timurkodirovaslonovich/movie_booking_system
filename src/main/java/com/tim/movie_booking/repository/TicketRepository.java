// repository/TicketRepository.java
package com.tim.movie_booking.repository;

import com.tim.movie_booking.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByBookingId(UUID bookingId);
    List<Ticket> findByShowTimeId(UUID showTimeId);
}