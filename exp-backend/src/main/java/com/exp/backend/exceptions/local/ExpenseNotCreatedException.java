package com.exp.backend.exceptions.local;

public class ExpenseNotCreatedException extends RuntimeException {
    public ExpenseNotCreatedException(String message) {
        super(message);
    }
}
