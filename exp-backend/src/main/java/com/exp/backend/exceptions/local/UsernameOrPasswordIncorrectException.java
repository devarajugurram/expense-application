package com.exp.backend.exceptions.local;

public class UsernameOrPasswordIncorrectException extends RuntimeException {
    public UsernameOrPasswordIncorrectException(String message) {
        super(message);
    }
}
