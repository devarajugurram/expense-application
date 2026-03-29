package com.exp.backend.exceptions.local;

public class OTPDidNotMatchException extends RuntimeException{
    public OTPDidNotMatchException(String message) {
        super(message);
    }
}
