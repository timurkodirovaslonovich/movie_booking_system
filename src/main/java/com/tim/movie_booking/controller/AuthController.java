package com.tim.movie_booking.controller;

// src/main/java/com/example/movie_booking_system/controller/AuthController.java

import com.tim.movie_booking.entity.Role;
import com.tim.movie_booking.entity.User;
import com.tim.movie_booking.repository.UserRepository;
import com.tim.movie_booking.security.JwtUtil;
import com.tim.movie_booking.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.tim.movie_booking.dto.RegisterRequest;
import com.tim.movie_booking.dto.LoginRequest;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) {
        User user = new User();
        user.setName(req.getName());                        // ✅ add this — it's NOT NULL in DB
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.valueOf(req.getRole()));                            // ✅ was Role.valueOf("ROLE_USER")
        userRepository.save(user);
        return ResponseEntity.ok("Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req) {
        // This throws an exception if credentials are wrong
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        UserDetails user = userDetailsService.loadUserByUsername(req.getEmail());
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(token);
    }
}
