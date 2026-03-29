package com.exp.backend.service;

import com.exp.backend.aop.MessageInterface;
import com.exp.backend.template.interfaces.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTServices implements TokenHelpers {

    private final Logger logger = LoggerFactory.getLogger(JWTServices.class);

    @Value("${app.secret.token}")
    private String secretToken;

    @MessageInterface
    @Override
    public String generateSessionToken(String email,long time) {
        Map<String,String> claims = new HashMap<>();
        logger.info("creating token for access and refresh token. USER : {}",email);
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + time))
                .and()
                .signWith(keyHelper())
                .compact();
    }

    @MessageInterface
    @Override
    public SecretKey keyHelper() {
        logger.info("generating key");
        return Keys.hmacShaKeyFor(secretToken.getBytes(StandardCharsets.UTF_8));
    }
}
