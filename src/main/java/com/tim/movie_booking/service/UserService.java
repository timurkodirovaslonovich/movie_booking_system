package com.tim.movie_booking.service;

import com.tim.movie_booking.dto.UserRequestDto;
import com.tim.movie_booking.dto.UserResponseDto;
import com.tim.movie_booking.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<UserResponseDto> getUserById(UUID id);
    List<UserResponseDto> getAllUsers();

    UserResponseDto createUser(UserRequestDto request);

    UserResponseDto toDto(User user);
}
