package com.exp.backend.controller;

import com.exp.backend.model.LoginModel;
import com.exp.backend.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 */
@RequestMapping("/api/v1/")
@RestController
@Validated
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<Map<String,String>> loginHere(@Valid
//            @RequestBody LoginModel loginModel
//            ) {
//        return loginService.login(loginModel);
//    }

}
