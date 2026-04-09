package com.tim.movie_booking.controller;


import com.tim.movie_booking.dto.*;
import com.tim.movie_booking.entity.User;
import com.tim.movie_booking.service.BookingService;
import com.tim.movie_booking.service.MovieService;
import com.tim.movie_booking.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/api/admin")
@Tag(name = "admin", description = "Admin management APIs")
public class AdminController {

    private final UserServiceImpl userService;
    private final BookingService bookingService;
    private final MovieService movieService;


    //dependency injection with controller
    public AdminController(UserServiceImpl userService, BookingService bookingService, MovieService movieService) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.movieService = movieService;
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


    @PostMapping("/movies")
    @Operation(summary = "Create movie")
    public ResponseEntity<MovieResponseDto> createMovie(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody MovieRequestDto request) {


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movieService.createMovie(currentUser, request));

    }


    @PutMapping("/movies/{uuid}")
    @Operation(summary = "updating movie")
    ResponseEntity<MovieResponseDto> updateMovie(
            @PathVariable UUID uuid,
            @Valid @RequestBody MovieRequestDto request,
            @AuthenticationPrincipal User currentUser)
    {
        return ResponseEntity.ok(movieService.updateMovie(uuid, request, currentUser));
    }


    @DeleteMapping("/movies/{uuid}")
    @Operation(summary = "deleting movie")
    ResponseEntity<Void> deleteMovie(@PathVariable UUID uuid) {
        movieService.deleteMovie(uuid);
        return ResponseEntity.noContent().build();
    }




    @GetMapping("/bookings")
    @Operation(summary = "Get all bookings — admin only")
    public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

}