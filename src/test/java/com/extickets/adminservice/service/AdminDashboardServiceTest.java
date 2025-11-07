package com.extickets.adminservice.service;

import com.extickets.adminservice.model.Ticket;
import com.extickets.adminservice.model.TicketWithStatus;
import com.extickets.adminservice.repository.AdminDashboardRepository;
import com.extickets.adminservice.repository.UploadTicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AdminDashboardServiceTest {

    @Mock
    private UploadTicketRepository ticketRepository;

    @Mock
    private AdminDashboardRepository adminRepository;

    @InjectMocks
    private AdminDashboardService adminService;

    private Ticket sampleTicket;
    private TicketWithStatus sampleTicketWithStatus;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleTicket = new Ticket();
        sampleTicket.setId("1");
        sampleTicket.setEventName("Concert");
        sampleTicket.setVenue("Arena");
        sampleTicket.setPrice(1200.0);
        sampleTicket.setEventDateTime(LocalDateTime.now());

        sampleTicketWithStatus = new TicketWithStatus("approved", LocalDateTime.now());
    }

//    @Test
//    void testSaveTicket() {
//        doNothing().when(ticketRepository).save(any(Ticket.class));
//
//        adminService.saveTicket(sampleTicket);
//
//        verify(ticketRepository, times(1)).save(sampleTicket);
//    }

    @Test
    void testGetAllTickets() {
        when(ticketRepository.findAll()).thenReturn(Collections.singletonList(sampleTicket));

        List<Ticket> result = adminService.getAllTickets();

        assertEquals(1, result.size());
        assertEquals("Concert", result.get(0).getEventName());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTicketsBasedOnStatus() {
        when(ticketRepository.findByStatus("approved")).thenReturn(Collections.singletonList(sampleTicketWithStatus));

        List<TicketWithStatus> result = adminService.getAllTicketsBasedOnStatus("approved");

        assertEquals(1, result.size());
        assertEquals("approved", result.get(0).getStatus());
        verify(ticketRepository, times(1)).findByStatus("approved");
    }

    @Test
    void testChangeStatus() {
        when(adminRepository.changeStatus(1L, "ADMIN", "approved", "ok")).thenReturn(true);

        boolean result = adminService.changeStatus(1L, "ADMIN", "approved", "ok");

        assertTrue(result);
        verify(adminRepository, times(1)).changeStatus(1L, "ADMIN", "approved", "ok");
    }

    @Test
    void testGetTicketById() {
        when(ticketRepository.findById(1L)).thenReturn(sampleTicket);

        Ticket result = adminService.getTicketById(1L);

        assertNotNull(result);
        assertEquals("Concert", result.getEventName());
        verify(ticketRepository, times(1)).findById(1L);
    }
}
