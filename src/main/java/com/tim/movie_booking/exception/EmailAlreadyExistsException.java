package com.tim.movie_booking.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;


public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
