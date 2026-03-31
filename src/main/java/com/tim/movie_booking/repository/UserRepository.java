package com.tim.movie_booking.repository;

import com.tim.movie_booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {
//    Optional<User> findUserById(UUID id);

//    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

}
