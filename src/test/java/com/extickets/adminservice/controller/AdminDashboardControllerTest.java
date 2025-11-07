package com.extickets.adminservice.controller;

import com.extickets.adminservice.model.Ticket;
import com.extickets.adminservice.model.TicketWithStatus;
import com.extickets.adminservice.service.AdminDashboardService;
import com.extickets.adminservice.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AdminDashboardControllerTest {

    @Mock
    private AdminDashboardService adminService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AdminDashboardController controller;

    private Ticket sampleTicket;
    private TicketWithStatus ticketWithStatus;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleTicket = new Ticket();
        sampleTicket.setId("1L");
        sampleTicket.setEventName("Music Fest");
        sampleTicket.setVenue("Hyderabad Arena");
        sampleTicket.setEventDateTime(LocalDateTime.of(2025, 12, 20, 18, 30));
        sampleTicket.setPrice(1500.0);

        ticketWithStatus = new TicketWithStatus("Pending", LocalDateTime.of(2025, 12, 20, 18, 30 ));
    }

    @Test
    void testGetAllTicketsBasedOnStatus() {
        when(adminService.getAllTicketsBasedOnStatus("Pending"))
                .thenReturn(Collections.singletonList(ticketWithStatus));

        ResponseEntity<List<TicketWithStatus>> response =
                controller.getAllTicketsBasedOnStatus("Pending");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Pending", response.getBody().get(0).getStatus());

        verify(adminService, times(1)).getAllTicketsBasedOnStatus("Pending");
    }

    @Test
    void testChangeStatus_Success() {
        when(adminService.getTicketById(1L)).thenReturn(sampleTicket);
        when(adminService.changeStatus(1L, "ADMIN", "Approved", "Looks valid"))
                .thenReturn(true);

        ResponseEntity<String> response =
                controller.changeStatus(1L, "Approved", "Looks valid");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ticket status changed successfully!", response.getBody());

        verify(adminService, times(1)).getTicketById(1L);
        verify(emailService, times(1)).sendEmail(any(Ticket.class), eq("Approved"));
        verify(adminService, times(1)).changeStatus(1L, "ADMIN", "Approved", "Looks valid");
    }

    @Test
    void testChangeStatus_TicketNotFound() {
        when(adminService.getTicketById(99L)).thenReturn(null);
        when(adminService.changeStatus(99L, "ADMIN", "Rejected", "Invalid ticket"))
                .thenReturn(false);

        ResponseEntity<String> response =
                controller.changeStatus(99L, "Rejected", "Invalid ticket");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No such ticket exists", response.getBody());

        verify(adminService, times(1)).getTicketById(99L);
        verify(emailService, times(1)).sendEmail(null, "Rejected");
        verify(adminService, times(1)).changeStatus(99L, "ADMIN", "Rejected", "Invalid ticket");
    }
}
