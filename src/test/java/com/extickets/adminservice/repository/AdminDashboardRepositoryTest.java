package com.extickets.adminservice.repository;

import com.extickets.adminservice.model.Ticket;
import com.extickets.adminservice.model.TicketWithStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AdminDashboardRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AdminDashboardRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll_ReturnsListOfTickets() {
        Ticket mockTicket = new Ticket();
        mockTicket.setId("1");
        mockTicket.setEventName("Music Fest");
        mockTicket.setVenue("Hyderabad Stadium");
        mockTicket.setPrice(1500.0);

        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Ticket>>any()))
                .thenReturn(List.of(mockTicket));

        List<Ticket> result = repository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Music Fest", result.get(0).getEventName());
        verify(jdbcTemplate, times(1))
                .query(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Ticket>>any());
    }

    @Test
    void testFindByStatus_ReturnsTicketsWithStatus() {
        TicketWithStatus mockTicket = new TicketWithStatus("Approved", LocalDateTime.now());
        mockTicket.setId("2");
        mockTicket.setEventName("Tech Expo");
        mockTicket.setVenue("Bangalore Palace");

        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<TicketWithStatus>>any(), anyString()))
                .thenReturn(List.of(mockTicket));

        List<TicketWithStatus> result = repository.findByStatus("Approved");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Approved", result.get(0).getStatus());
        assertEquals("Tech Expo", result.get(0).getEventName());
        verify(jdbcTemplate, times(1))
                .query(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<TicketWithStatus>>any(), eq("Approved"));
    }

    @Test
    void testChangeStatus_ReturnsTrue_WhenUpdateSucceeds() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyString(), anyLong()))
                .thenReturn(1);

        boolean result = repository.changeStatus(1L, "Admin", "Approved", "Looks good");

        assertTrue(result);
        verify(jdbcTemplate, times(1))
                .update(anyString(), eq("Approved"), eq("Admin"), eq("Looks good"), eq(1L));
    }

    @Test
    void testChangeStatus_ReturnsFalse_WhenNoRowsUpdated() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyString(), anyLong()))
                .thenReturn(0);

        boolean result = repository.changeStatus(1L, "Admin", "Rejected", "Invalid ticket");

        assertFalse(result);
        verify(jdbcTemplate, times(1))
                .update(anyString(), eq("Rejected"), eq("Admin"), eq("Invalid ticket"), eq(1L));
    }
}
