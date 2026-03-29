package com.exp.backend.service;

import com.exp.backend.config.UserDetail;
import com.exp.backend.exceptions.local.TokenIsNotValidException;
import com.exp.backend.exceptions.local.UnauthorizedUserException;
import com.exp.backend.exceptions.local.UsernameOrPasswordIncorrectException;
import com.exp.backend.template.helpers.HelperComponentForToken;
import com.exp.backend.template.helpers.ResponseHelperMethods;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.security.auth.RefreshFailedException;
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

    public ResponseEntity<Map<String,Object>> refreshToken(String refreshToken)
            throws TokenIsNotValidException {

        if(refreshToken == null || refreshToken.isBlank()) {
            throw new TokenIsNotValidException( "Token is Not Valid");
        }

        try {

            String userName = helperComponentForToken.extractUserName(refreshToken);

            UserDetails userDetails = userDetail.loadUserByUsername(userName);

            if(!helperComponentForToken.validateToken(refreshToken, userDetails)) {
                throw new TokenIsNotValidException("Token is not valid");
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
                            HttpStatus.OK));

        } catch (Exception e) {
            throw new TokenIsNotValidException("Token is not Valid");
        }
    }
}
