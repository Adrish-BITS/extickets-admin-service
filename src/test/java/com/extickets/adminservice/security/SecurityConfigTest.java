package com.extickets.adminservice.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    private JwtFilter jwtFilter;
    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        jwtFilter = mock(JwtFilter.class);
        securityConfig = new SecurityConfig(jwtFilter);
    }

    @Test
    void testCorsConfigurationSource() {
        CorsConfigurationSource source = securityConfig.corsConfigurationSource();
        assertNotNull(source);

        CorsConfiguration config = source.getCorsConfiguration(null);
        assertNotNull(config);

        assertEquals(List.of("http://localhost:3000"), config.getAllowedOrigins());
        assertEquals(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"), config.getAllowedMethods());
        assertEquals(List.of("Authorization", "Content-Type"), config.getAllowedHeaders());
        assertTrue(config.getAllowCredentials());
    }

    @Test
    void testSecurityFilterChainBuildsSuccessfully() throws Exception {
        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        // Mock necessary methods
        when(httpSecurity.cors(any())).thenReturn(httpSecurity);
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.addFilterBefore(any(), any())).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn((DefaultSecurityFilterChain) mock(SecurityFilterChain.class));

        SecurityFilterChain filterChain = securityConfig.securityFilterChain(httpSecurity);

        assertNotNull(filterChain);
        verify(httpSecurity).addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
    }
}
