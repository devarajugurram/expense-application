package com.exp.backend.template.interfaces;

import io.jsonwebtoken.Claims;

import java.util.function.Function;

/**
 *
 */
public interface ClaimHelpers {
    <T> T extractClaim(String token, Function<Claims, T> claimsTFunction);
    String extractUserName(String token);
    Claims extractAllClaims(String token);
}
