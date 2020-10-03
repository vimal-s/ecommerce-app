package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class EmptyOrderNotAllowedException extends RuntimeException {

    public EmptyOrderNotAllowedException() {
        super("No items are present in the cart");
    }
}
