package com.tim.movie_booking.controller;


import com.tim.movie_booking.dto.BookingResponseDto;
import com.tim.movie_booking.dto.UserRequestDto;
import com.tim.movie_booking.dto.UserResponseDto;
import com.tim.movie_booking.service.BookingService;
import com.tim.movie_booking.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/api/admin")
@Tag(name = "User", description = "User management APIs")
public class AdminController {

    private final UserServiceImpl userService;
    private final BookingService bookingService;
    //dependency injection with controller
    public AdminController(UserServiceImpl userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @GetMapping("/users")
    @Operation(summary = "Getting all users")
    ResponseEntity<List<UserResponseDto>> getAllUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "getting user by ID")
    ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        UserResponseDto user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }


    @PostMapping("/users")
    @Operation(summary = "postign a user")
    ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request ) {
        var user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


    @PutMapping("/users")
    @Operation(summary = "Updating user name or/and password")
    ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserRequestDto request) {
        var updatedUser = userService.updateUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }

    @DeleteMapping("/users/{uuid}")
    @Operation(summary = "Deleting user")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) {  // ✅ fixed return type
        userService.deleteUser(uuid);
        return ResponseEntity.noContent().build(); // ✅ proper 204 response
    }


    @GetMapping("/bookings")
    @Operation(summary = "Get all bookings — admin only")
    public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

}