package com.extickets.adminservice.service;

import com.extickets.adminservice.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private RestTemplate restTemplate;

    private Ticket ticket;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ticket = new Ticket();
        ticket.setEventName("Rock Concert");
        ticket.setVenue("Arena");
        ticket.setPrice(1200.0);
        ticket.setEventDateTime(LocalDateTime.now());
        ticket.setEventImagePath("http://image-url");

        // Inject mock RestTemplate into EmailService
        Field field = EmailService.class.getDeclaredField("restTemplate");
        field.setAccessible(true);
        field.set(emailService, restTemplate);
    }

    @Test
    void testSendEmail_ApprovedStatus() {
        ResponseEntity<String> mockResponse = new ResponseEntity<>("OK", HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);

        emailService.sendEmail(ticket, "Approved");

        verify(restTemplate, atLeastOnce())
                .postForEntity(contains("http://localhost:8082"), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void testSendEmail_RejectedStatus() {
        ResponseEntity<String> mockResponse = new ResponseEntity<>("OK", HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);

        emailService.sendEmail(ticket, "Rejected");

        verify(restTemplate, atLeastOnce())
                .postForEntity(contains("http://localhost:8082"), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void testSendRequestToEmailService_ExceptionHandling() {
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RuntimeException("Connection refused"));

        emailService.sendEmail(ticket, "Approved");

        verify(restTemplate, atLeastOnce())
                .postForEntity(anyString(), any(HttpEntity.class), eq(String.class));
    }
}
