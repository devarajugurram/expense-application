package com.exp.backend.template.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

/**
 *
 */
public interface ValidateTokens {
    boolean validateToken(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
    Date extractExpiration(String token);
}
