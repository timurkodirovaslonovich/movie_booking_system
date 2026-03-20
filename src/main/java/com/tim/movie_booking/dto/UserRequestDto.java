package com.tim.movie_booking.dto;

import com.tim.movie_booking.entity.Role;

import java.util.UUID;

public class UserRequestDto {
    private UUID id;
    private String name;
    private String email;
    private Role role;
    private String password;

    public UserRequestDto() {
    }

    public UserRequestDto(UUID id, String name, String email, Role role, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;

    }

    public  UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
