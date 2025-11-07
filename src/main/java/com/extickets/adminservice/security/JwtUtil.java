package com.extickets.adminservice.security;

import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {


    private static final String SECRET_KEY = "ExTicketsSecretKeyExTicketsSecretKey12345";
    
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}