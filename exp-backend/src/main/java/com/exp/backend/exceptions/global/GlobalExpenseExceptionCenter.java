package com.exp.backend.exceptions.global;

import com.exp.backend.exceptions.local.ExpenseNotAvailableException;
import com.exp.backend.exceptions.local.ExpenseNotCreatedException;
import com.exp.backend.exceptions.local.ExpenseNotFoundException;
import com.exp.backend.template.helpers.ResponseHelperMethods;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;


@RestControllerAdvice(basePackages = "com.exp.backend.controller")
public class GlobalExpenseExceptionCenter {
    private final ResponseHelperMethods responseHelperMethods;

    public GlobalExpenseExceptionCenter(
            ResponseHelperMethods responseHelperMethods) {
        this.responseHelperMethods = responseHelperMethods;
    }

    @ExceptionHandler(ExpenseNotCreatedException.class)
    public ResponseEntity<Map<String,Object>> expenseNotCreated(ExpenseNotCreatedException expenseNotCreatedException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                responseHelperMethods.getRegistrationResponseHelper(
                        expenseNotCreatedException.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        LocalDateTime.now())
        );
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<Map<String,Object>> expenseNotFound(
            ExpenseNotFoundException expenseNotFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    @ExceptionHandler(ExpenseNotAvailableException.class)
    public ResponseEntity<Map<String,Object>> expenseNotAvailable(
            ExpenseNotAvailableException expenseNotAvailableException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseHelperMethods.getRegistrationResponseHelper(
                        expenseNotAvailableException.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        LocalDateTime.now()));
    }
}
