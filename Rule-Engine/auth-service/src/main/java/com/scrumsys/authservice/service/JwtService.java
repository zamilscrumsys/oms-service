package com.scrumsys.authservice.service;



import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiry;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // ========================
    // CREATE ACCESS TOKEN
    // ========================
    public String generateAccessToken(
            String username,
            List<String> roles) {

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + accessTokenExpiry))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ========================
    // VALIDATE TOKEN
    // ========================
    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ========================
    // GET USERNAME
    // ========================
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ========================
    // GET ROLES
    // ========================
    public List<String> extractRoles(String token) {
        return extractAllClaims(token)
                .get("role", List.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateRefreshToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + (30 * 60 * 1000))) // 30 minutes
                .claim("type", "REFRESH")
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

