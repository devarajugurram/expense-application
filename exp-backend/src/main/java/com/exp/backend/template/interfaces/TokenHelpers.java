package com.exp.backend.template.interfaces;

import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.util.function.Function;

/**
 *
 */
public interface TokenHelpers extends JWTServiceImpl {
    SecretKey keyHelper();
}

