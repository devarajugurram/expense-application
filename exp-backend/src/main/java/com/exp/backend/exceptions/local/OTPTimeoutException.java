package com.exp.backend.exceptions.local;

public class OTPTimeoutException extends RuntimeException {
    public OTPTimeoutException(String message) {
        super(message);
    }
}
