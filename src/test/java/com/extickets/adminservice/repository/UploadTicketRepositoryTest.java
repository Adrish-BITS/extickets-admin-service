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

class UploadTicketRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UploadTicketRepository repository;

    private Ticket ticket;
    private TicketWithStatus ticketWithStatus;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ticket = new Ticket();
        ticket.setEventName("Music Fest");
        ticket.setEventDateTime(LocalDateTime.of(2025, 11, 20, 19, 0));
        ticket.setVenue("Hyderabad Stadium");
        ticket.setPrice(1200.0);
        ticket.setFilePath("s3://bucket/file.pdf");
        ticket.setEventImagePath("s3://bucket/image.png");
        ticket.setUploadedUserName("John Doe");
        ticket.setUploadedEmail("john@example.com");

        ticketWithStatus = new TicketWithStatus("approved", LocalDateTime.now());
        ticketWithStatus.setEventName("Art Expo");
    }

    @Test
    void testSave_SuccessfulInsert() {
        // Mock sequence of DB calls
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(1);
        when(jdbcTemplate.queryForObject(eq("SELECT LAST_INSERT_ID()"), eq(Long.class))).thenReturn(1L);
        when(jdbcTemplate.update(eq("INSERT INTO transactions (ticket_id, status) VALUES (?, ?)"), anyLong(), anyString()))
                .thenReturn(1);

        int result = repository.save(ticket);

        assertEquals(0, result); // always returns 0
        verify(jdbcTemplate, times(1)).update(contains("INSERT INTO tickets"), any(), any(), any(), any(), any(), any(), any(), any());
        verify(jdbcTemplate, times(1)).queryForObject(eq("SELECT LAST_INSERT_ID()"), eq(Long.class));
        verify(jdbcTemplate, times(1)).update(eq("INSERT INTO transactions (ticket_id, status) VALUES (?, ?)"), eq(1L), eq("in-review"));
    }

    @Test
    void testFindAll_ReturnsTickets() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Ticket>>any()))
                .thenReturn(List.of(ticket));

        List<Ticket> result = repository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Music Fest", result.get(0).getEventName());
        verify(jdbcTemplate, times(1)).query(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Ticket>>any());
    }

    @Test
    void testFindByStatus_ReturnsTicketsWithStatus() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<TicketWithStatus>>any(), anyString()))
                .thenReturn(List.of(ticketWithStatus));

        List<TicketWithStatus> result = repository.findByStatus("approved");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("approved", result.get(0).getStatus());
        verify(jdbcTemplate, times(1))
                .query(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<TicketWithStatus>>any(), eq("approved"));
    }

    @Test
    void testFindById_ReturnsTicket() {
        when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Ticket>>any(), anyLong()))
                .thenReturn(ticket);

        Ticket result = repository.findById(1L);

        assertNotNull(result);
        assertEquals("Music Fest", result.getEventName());
        verify(jdbcTemplate, times(1))
                .queryForObject(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Ticket>>any(), eq(1L));
    }

    @Test
    void testFindByUserEmail_ReturnsTickets() {
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Ticket>>any(), anyString()))
                .thenReturn(List.of(ticket));

        List<Ticket> result = repository.findByUserEmail("john@example.com");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Music Fest", result.get(0).getEventName());
        verify(jdbcTemplate, times(1))
                .query(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Ticket>>any(), eq("john@example.com"));
    }
}
