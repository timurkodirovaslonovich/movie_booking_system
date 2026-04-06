package com.tim.movie_booking.service;


import com.tim.movie_booking.dto.UserRequestDto;
import com.tim.movie_booking.dto.UserResponseDto;
import com.tim.movie_booking.entity.User;
import com.tim.movie_booking.exception.ResourceNotFoundException;
import com.tim.movie_booking.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //dependency injection with constructor method
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //getting user based on the id
    @Override
    public UserResponseDto getUserById(UUID id) {
        return userRepository.findById(id)
                .map(this::toDto).orElseThrow(()-> new ResourceNotFoundException("User not found with id: " + id));
    }


    //getting all the users
    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
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

    @Override
    public UserResponseDto updateMe(User currentUser, UserRequestDto request) {
        // ✅ currentUser came from SecurityContext — it has the ID already
        if (request.getName() != null && !request.getName().isBlank()) {
            currentUser.setName(request.getName());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            currentUser.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            currentUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User saved = userRepository.save(currentUser); // ✅ has ID → UPDATE
        return toDto(saved);
    }



    @Override
    public UserResponseDto updateUser(UserRequestDto request) {
        if (userRepository.findById(request.getId()).isPresent()) {
            User updatesUser = new User(
                    request.getName(),
                    request.getEmail(),
                    request.getRole(),
                    request.getPassword()
            );

            return toDto(userRepository.save(updatesUser));
        } else {
            throw new RuntimeException("User doesnot exist");
        }
    }


    @Override
    public void deleteUser(UUID uuid) {
       if (userRepository.existsById(uuid)) {
           userRepository.deleteById(uuid);
       } else {
           throw new RuntimeException("User not found");
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
