package com.exp.backend.exceptions.global;

import com.exp.backend.exceptions.local.*;
import com.exp.backend.template.helpers.ResponseHelperMethods;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;


@RestControllerAdvice(basePackages = "com.exp.backend.controller")
public class GlobalSecurityExceptionCenter {

    private final ResponseHelperMethods responseHelperMethods;

    public GlobalSecurityExceptionCenter(ResponseHelperMethods responseHelperMethods) {
        this.responseHelperMethods = responseHelperMethods;
    }

    @ExceptionHandler(OTPDidNotMatchException.class)
    public ResponseEntity<Map<String,Object>> otpDidNotMatch(
            OTPDidNotMatchException otpDidNotMatchException) {
        return ResponseEntity.ok(
                responseHelperMethods.getRegistrationResponseHelper(
                        otpDidNotMatchException.getMessage(),
                        HttpStatus.UNAUTHORIZED,
                        LocalDateTime.now()
                ));
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,Object>> registrationException(
            UserNotFoundException userNotFoundException) {
        return ResponseEntity.ok(responseHelperMethods.getRegistrationResponseHelper(
                userNotFoundException.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        ));
    }


    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Map<String,Object>> userAlreadyExist(
            UserAlreadyExistException userAlreadyExistException) {
        return ResponseEntity.ok(responseHelperMethods
                .getRegistrationResponseHelper(
                        userAlreadyExistException.getMessage(),
                        HttpStatus.CONFLICT,
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(OTPSentUnsuccessfulException.class)
    public ResponseEntity<Map<String,Object>> otpSentUnsuccessful(
            OTPSentUnsuccessfulException otpSentUnsuccessfulException) {
        Map<String,Object> res = responseHelperMethods
                .getRegistrationResponseHelper(
                        otpSentUnsuccessfulException.getMessage(),
                        HttpStatus.NOT_FOUND,
                        LocalDateTime.now()
                );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }


    @ExceptionHandler(UsernameOrPasswordIncorrectException.class)
    public ResponseEntity<Map<String,Object>> usernameOrPasswordException(
            UsernameOrPasswordIncorrectException usernameOrPasswordIncorrectException) {
        return ResponseEntity.badRequest().body(
                responseHelperMethods.getRegistrationResponseHelper(
                        usernameOrPasswordIncorrectException.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        LocalDateTime.now()));
    }


    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<Map<String,Object>> unauthorized(
            UnauthorizedUserException unauthorizedUserException){
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }


    @ExceptionHandler(TokenIsNotValidException.class)
    public ResponseEntity<Map<String,Object>> tokenIsNotValid(
            TokenIsNotValidException tokenIsNotValidException){
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }


}
