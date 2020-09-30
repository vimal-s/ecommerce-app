package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordConfirmationException extends RuntimeException {

    public PasswordConfirmationException(String message) {
        super(message);
    }
}
