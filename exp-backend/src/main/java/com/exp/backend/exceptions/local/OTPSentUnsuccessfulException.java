package com.exp.backend.exceptions.local;

public class OTPSentUnsuccessfulException extends RuntimeException {
    public OTPSentUnsuccessfulException(String message) {
        super(message);
    }
}
