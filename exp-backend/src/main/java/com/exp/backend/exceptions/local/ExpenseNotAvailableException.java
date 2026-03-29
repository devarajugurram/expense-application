package com.exp.backend.exceptions.local;

public class ExpenseNotAvailableException extends RuntimeException {
    public ExpenseNotAvailableException(String message) {
        super(message);
    }
}
