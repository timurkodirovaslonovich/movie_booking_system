package com.tim.movie_booking.dto;



import com.tim.movie_booking.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;



@Getter
@Setter
public class UserResponseDto {
    private UUID id;
    private String name;
    private String email;
    private Role role;

    public UserResponseDto() {
    }

    public UserResponseDto(UUID id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

}
