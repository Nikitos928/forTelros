package com.example.demo.exception;

public class BadRequestException extends Exception {
    public BadRequestException() {
    }

    public BadRequestException(final String message) {
        super(message);
    }

}
