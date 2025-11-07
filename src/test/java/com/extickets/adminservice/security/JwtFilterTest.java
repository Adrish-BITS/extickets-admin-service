package com.extickets.adminservice.security;

import com.extickets.adminservice.model.GoogleUserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext(); // clear context before every test
    }

    @Test
    void testDoFilterInternal_WithValidToken_SetsAuthentication() throws ServletException, IOException {
        String token = "valid.jwt.token";
        Claims claims = new DefaultClaims();
        claims.put("email", "test@example.com");
        claims.put("name", "John Doe");

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractAllClaims(token)).thenReturn(claims);

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        GoogleUserPrincipal user =
                (GoogleUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assertEquals("test@example.com", user.getEmail());
        assertEquals("John Doe", user.getName());

        verify(jwtUtil, times(1)).extractAllClaims(token);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithInvalidToken_DoesNotAuthenticate() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid.token");
        when(jwtUtil.extractAllClaims(anyString())).thenThrow(new RuntimeException("Invalid token"));

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithNoAuthorizationHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithEmptyBearerHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer ");

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithAlreadyAuthenticatedUser() throws ServletException, IOException {
        // Set an existing authentication in the context
        SecurityContextHolder.getContext().setAuthentication(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("existingUser", null, null)
        );

        when(request.getHeader("Authorization")).thenReturn("Bearer some.token");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, never()).extractAllClaims(anyString());
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
