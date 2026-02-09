package com.scrumsys.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String secret = "your-jwt-secret"; // USE same as auth-service

    public Claims validateToken(String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}