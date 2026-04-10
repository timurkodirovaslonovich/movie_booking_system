package com.tim.movie_booking.controller;

import com.tim.movie_booking.dto.UserRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.tim.movie_booking.dto.UserResponseDto;
import com.tim.movie_booking.repository.UserRepository;
import com.tim.movie_booking.service.UserService;
import com.tim.movie_booking.entity.User;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class OwnUserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyProfile(
        @AuthenticationPrincipal User currentUser
     ) {
        return ResponseEntity.ok(userService.toDto(currentUser));
     }


    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateMyProfile(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.updateMe(currentUser, request));
    }

}
