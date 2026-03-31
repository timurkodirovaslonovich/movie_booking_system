package com.tim.movie_booking.controller;


import com.tim.movie_booking.dto.UserRequestDto;
import com.tim.movie_booking.dto.UserResponseDto;
import com.tim.movie_booking.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User management APIs")
class UserController {

    private final UserServiceImpl userService;
    //dependency injection with controller
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Getting all users")
    ResponseEntity<List<UserResponseDto>> getAllUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "getting user by ID")
    ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        Optional<UserResponseDto> user = userService.getUserById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    @Operation(summary = "postign a user")
    ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request ) {
        var user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


    @PutMapping
    @Operation(summary = "Updating user name or/and password")
    ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserRequestDto request) {
        var updatedUser = userService.updateUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }
    

}