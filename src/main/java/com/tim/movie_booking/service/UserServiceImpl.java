package com.tim.movie_booking.service;


import com.tim.movie_booking.dto.UserRequestDto;
import com.tim.movie_booking.dto.UserResponseDto;
import com.tim.movie_booking.entity.User;
import com.tim.movie_booking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    //dependency injection with constructor method
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //getting user based on the id
    @Override
    public Optional<UserResponseDto> getUserById(UUID id) {
        return userRepository.findById(id)
                .map(this::toDto);
    }


    //getting all the users
    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto).toList();
    }


    //creating user
    @Override
    public UserResponseDto createUser(UserRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        } else {
            User user = new User(
                    request.getName(),
                    request.getEmail(),
                    request.getRole(),
                    request.getPassword()
            );

            User savedUser = userRepository.save(user);
            return toDto(savedUser);
        }
    }




    /// mapping entity to response dtos
    @Override
    public UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
