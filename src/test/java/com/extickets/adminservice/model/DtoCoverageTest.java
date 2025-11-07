package com.extickets.adminservice.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DtoCoverageTest {

    @Test
    void testGoogleUserPrincipal() {
        GoogleUserPrincipal principal = new GoogleUserPrincipal("user@example.com", "John Doe");

        assertEquals("user@example.com", principal.getEmail());
        assertEquals("John Doe", principal.getName());
    }

    @Test
    void testTicketGettersAndSetters() {
        Ticket ticket = new Ticket();
        ticket.setId("1");
        ticket.setEventName("Music Fest");
        ticket.setEventDateTime(LocalDateTime.of(2025, 11, 5, 18, 30));
        ticket.setVenue("Hyderabad Stadium");
        ticket.setPrice(999.99);
        ticket.setFilePath("/tickets/file.pdf");
        ticket.setEventImagePath("/images/event.png");
        ticket.setUploadedDateTime("2025-11-05T18:31");
        ticket.setUploadedUserName("John Doe");
        ticket.setUploadedEmail("john@example.com");

        assertEquals("1", ticket.getId());
        assertEquals("Music Fest", ticket.getEventName());
        assertEquals(LocalDateTime.of(2025, 11, 5, 18, 30), ticket.getEventDateTime());
        assertEquals("Hyderabad Stadium", ticket.getVenue());
        assertEquals(999.99, ticket.getPrice());
        assertEquals("/tickets/file.pdf", ticket.getFilePath());
        assertEquals("/images/event.png", ticket.getEventImagePath());
        assertEquals("2025-11-05T18:31", ticket.getUploadedDateTime());
        assertEquals("John Doe", ticket.getUploadedUserName());
        assertEquals("john@example.com", ticket.getUploadedEmail());
    }

    @Test
    void testTicketWithStatusConstructorAndMethods() {
        LocalDateTime now = LocalDateTime.now();
        TicketWithStatus ticketWithStatus = new TicketWithStatus("Approved", now);

        // Test inherited setters
        ticketWithStatus.setId("2");
        ticketWithStatus.setEventName("Movie Premiere");
        ticketWithStatus.setVenue("PVR Cinema");
        ticketWithStatus.setPrice(499.0);

        // Validate values
        assertEquals("Approved", ticketWithStatus.getStatus());
        assertEquals(now, ticketWithStatus.getUpdatedAt());
        assertEquals("2", ticketWithStatus.getId());
        assertEquals("Movie Premiere", ticketWithStatus.getEventName());
        assertEquals("PVR Cinema", ticketWithStatus.getVenue());
        assertEquals(499.0, ticketWithStatus.getPrice());

        // Test setter update
        LocalDateTime updatedTime = now.plusDays(1);
        ticketWithStatus.setUpdatedAt(updatedTime);
        ticketWithStatus.setStatus("Rejected");
        assertEquals("Rejected", ticketWithStatus.getStatus());
        assertEquals(updatedTime, ticketWithStatus.getUpdatedAt());
    }

    @Test
    void testTransactionGettersAndSetters() {
        Transaction transaction = new Transaction();
        LocalDateTime updateTime = LocalDateTime.of(2025, 11, 10, 12, 0);

        transaction.setTransactionId(101L);
        transaction.setTicketId(202L);
        transaction.setStatus("Approved");
        transaction.setUpdatedAt(updateTime);

        assertEquals(101L, transaction.getTransactionId());
        assertEquals(202L, transaction.getTicketId());
        assertEquals("Approved", transaction.getStatus());
        assertEquals(updateTime, transaction.getUpdatedAt());
    }
}
