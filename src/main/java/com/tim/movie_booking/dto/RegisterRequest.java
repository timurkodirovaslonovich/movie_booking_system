package com.tim.movie_booking.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String role;// ✅ add this
    private String email;
    private String password;
}
