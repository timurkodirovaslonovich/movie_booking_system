package com.tim.movie_booking.dto;

import com.tim.movie_booking.entity.Role;

import java.util.UUID;


public class UserUpdateRequest {

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public UserUpdateRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
