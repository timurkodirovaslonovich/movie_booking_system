package com.tim.movie_booking.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BookingRequestDto {

    @NotNull(message = "ShowTime ID is required")
    private UUID showTimeId;

    @NotNull(message = "Seat list is required")
    @NotEmpty(message = "At least one seat must be selected")
    private List<UUID> seatIds;
}
