package com.tim.movie_booking.service;

import com.tim.movie_booking.dto.UserRequestDto;
import com.tim.movie_booking.dto.UserResponseDto;
import com.tim.movie_booking.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDto getUserById(UUID id);
    List<UserResponseDto> getAllUsers();

    UserResponseDto createUser(UserRequestDto request);

    UserResponseDto updateUser(UserRequestDto request);
    UserResponseDto updateMe(User currentUser, UserRequestDto request);

    void deleteUser(UUID uuid);

    UserResponseDto toDto(User user);


}
