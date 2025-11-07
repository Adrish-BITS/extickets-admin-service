package com.extickets.adminservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private Key key;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        key = Keys.hmacShaKeyFor("ExTicketsSecretKeyExTicketsSecretKey12345".getBytes());
    }

    @Test
    void testExtractAllClaims_ValidToken() {
        // Create a test JWT
        String token = Jwts.builder()
                .setSubject("test@example.com")
                .claim("email", "test@example.com")
                .claim("name", "John Doe")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        Claims claims = jwtUtil.extractAllClaims(token);

        assertNotNull(claims);
        assertEquals("test@example.com", claims.getSubject());
        assertEquals("test@example.com", claims.get("email", String.class));
        assertEquals("John Doe", claims.get("name", String.class));
    }

    @Test
    void testExtractAllClaims_InvalidToken_ThrowsException() {
        String invalidToken = "invalid.jwt.token";

        Exception exception = assertThrows(Exception.class, () -> {
            jwtUtil.extractAllClaims(invalidToken);
        });

        assertTrue(exception.getMessage().contains("JWT"), "Should throw JWT-related exception");
    }
}
