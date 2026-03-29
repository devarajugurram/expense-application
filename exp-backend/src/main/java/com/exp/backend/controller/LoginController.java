package com.exp.backend.controller;

import com.exp.backend.model.LoginModel;
import com.exp.backend.service.LoginService;
import com.exp.backend.service.RefreshService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.Refreshable;
import java.util.Map;

/**
 *
 */
@RequestMapping("/api/v1/")
@RestController
@Validated
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final LoginService loginService;
    private final RefreshService refreshService;

    /**
     *
     *
     * @param loginService
     */

    public LoginController(LoginService loginService,
                           RefreshService refreshService) {
        this.loginService = loginService;
        this.refreshService = refreshService;
    }

    /**
     *
     *
     * @param loginModel
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> loginHere(@Valid
            @RequestBody LoginModel loginModel, HttpServletResponse response
            ) {
        return loginService.login(loginModel,response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String,Object>> refreshToken(
            @RequestBody String refreshToken) {
        return refreshService.refreshToken(refreshToken);
    }

}
