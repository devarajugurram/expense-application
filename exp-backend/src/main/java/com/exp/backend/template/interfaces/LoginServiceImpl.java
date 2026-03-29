package com.exp.backend.template.interfaces;

import com.exp.backend.model.LoginModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;


/**
 *
 */
@FunctionalInterface
public interface LoginServiceImpl {
    ResponseEntity<Map<String,Object>> login(LoginModel loginModel, HttpServletResponse response);
}
