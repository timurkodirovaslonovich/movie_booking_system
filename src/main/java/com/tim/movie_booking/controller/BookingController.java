
package com.tim.movie_booking.controller;

import com.tim.movie_booking.dto.BookingRequestDto;
import com.tim.movie_booking.dto.BookingResponseDto;
import com.tim.movie_booking.entity.User;
import com.tim.movie_booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking", description = "Booking management APIs")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Create a new booking")
    public ResponseEntity<BookingResponseDto> createBooking(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody BookingRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.createBooking(currentUser, request));
    }

    @GetMapping("/my")
    @Operation(summary = "Get my bookings")
    public ResponseEntity<List<BookingResponseDto>> getMyBookings(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(bookingService.getMyBookings(currentUser));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID")
    public ResponseEntity<BookingResponseDto> getBookingById(
            @PathVariable UUID id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(bookingService.getBookingById(id, currentUser));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel a booking")
    public ResponseEntity<BookingResponseDto> cancelBooking(
            @PathVariable UUID id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(bookingService.cancelBooking(id, currentUser));
    }

//    @GetMapping("/admin/all")
//    @PreAuthorize("hasRole('ADMIN')")
//    @Operation(summary = "Get all bookings — admin only")
//    public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
//        return ResponseEntity.ok(bookingService.getAllBookings());
//    }
}