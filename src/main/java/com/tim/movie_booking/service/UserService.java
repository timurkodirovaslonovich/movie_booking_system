package com.tim.movie_booking.service;

import com.tim.movie_booking.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> getUserById(UUID id);
    List<User> getAllUsers();
}
