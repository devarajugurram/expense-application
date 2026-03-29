package com.exp.backend.service;

import com.exp.backend.config.UserDetail;
import com.exp.backend.template.helpers.HelperComponentForToken;
import com.exp.backend.template.helpers.ResponseHelperMethods;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class RefreshService {

    private final HelperComponentForToken helperComponentForToken;
    private final UserDetail userDetail;
    private final JWTServices jwtServices;
    private final ResponseHelperMethods responseHelperMethods;

    public RefreshService(HelperComponentForToken helperComponentForToken,
                          UserDetail userDetail,
                          JWTServices jwtServices,
                          ResponseHelperMethods responseHelperMethods) {
        this.helperComponentForToken = helperComponentForToken;
        this.userDetail = userDetail;
        this.jwtServices = jwtServices;
        this.responseHelperMethods = responseHelperMethods;
    }

    public ResponseEntity<Map<String,Object>> refreshToken(String refreshToken) {

        if(refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }

        try {

            String userName = helperComponentForToken.extractUserName(refreshToken);

            UserDetails userDetails = userDetail.loadUserByUsername(userName);

            if(!helperComponentForToken.validateToken(refreshToken, userDetails)) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
            }

            String newAccessToken =
                    jwtServices.generateSessionToken(userName,1000 * 60 * 15);

            ResponseCookie accessToken = ResponseCookie.from("accessToken", newAccessToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(60 * 15)
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, accessToken.toString())
                    .body(responseHelperMethods.getLoginResponseHelper(
                            LocalDateTime.now(),
                            "200"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }
    }
}
