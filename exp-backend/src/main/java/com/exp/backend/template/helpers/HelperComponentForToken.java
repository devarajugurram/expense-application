package com.exp.backend.template.helpers;

import com.exp.backend.aop.MessageInterface;
import com.exp.backend.template.interfaces.ClaimHelpers;
import com.exp.backend.template.interfaces.ValidateTokens;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 *
 */
@Component
public class HelperComponentForToken implements ValidateTokens, ClaimHelpers {
    private final Logger logger = LoggerFactory.getLogger(HelperComponentForToken.class);

    @Value("${app.secret.token}")
    private String secretKey;

    /**
     *
     * @param token
     * @param function
     * @return
     * @param <T>
     */
    @MessageInterface
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> function) {
        final Claims claims = extractAllClaims(token);
        return function.apply(claims);
    }

    /**
     *
     * @param token
     * @return
     */
    @MessageInterface
    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     *
     * @param token
     * @return
     */
    @MessageInterface
    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     *
     * @param token
     * @param userDetails
     * @return
     */
    @MessageInterface
    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     *
     * @param token
     * @return
     */
    @MessageInterface
    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     *
     * @param token
     * @return
     */
    @MessageInterface
    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
