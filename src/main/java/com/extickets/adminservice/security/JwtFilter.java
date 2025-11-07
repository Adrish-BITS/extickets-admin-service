package com.extickets.adminservice.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.extickets.adminservice.model.GoogleUserPrincipal;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
            	Claims claims = jwtUtil.extractAllClaims(token);  // extract all data
                String email = claims.get("email", String.class);
                String name = claims.get("name", String.class);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                	GoogleUserPrincipal user = new GoogleUserPrincipal(email, name);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, null);

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // Optional: log user info
                    System.out.println("✅ Authenticated user: " + name + " (" + email + ")");
                }
            } catch (Exception e) {
                System.out.println("Invalid or expired JWT: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
