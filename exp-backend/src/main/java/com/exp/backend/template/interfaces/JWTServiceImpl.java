package com.exp.backend.template.interfaces;

/**
 *
 */
public interface JWTServiceImpl {
    String generateSessionToken(String email,long time);
}
