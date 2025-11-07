package com.extickets.adminservice.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.extickets.adminservice.model.Ticket;
import com.extickets.adminservice.model.TicketWithStatus;


@Repository
public class UploadTicketRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Ticket ticket) {
        String INSERT_TO_TICKETS = "INSERT INTO tickets (event_name, event_date_time, venue, price, file_path, event_image_path, uploaded_username, uploaded_email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(INSERT_TO_TICKETS,
                ticket.getEventName(),
                Timestamp.valueOf(ticket.getEventDateTime()),
                ticket.getVenue(),
                ticket.getPrice(),
                ticket.getFilePath(),
                ticket.getEventImagePath(),
                ticket.getUploadedUserName(),
        		ticket.getUploadedEmail());
        
        String GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
        Long ticketId = jdbcTemplate.queryForObject(GET_LAST_INSERT_ID, Long.class);
        
        String INSERT_TO_TRANSACTIONS = "INSERT INTO transactions (ticket_id, status) VALUES (?, ?)";
        jdbcTemplate.update(INSERT_TO_TRANSACTIONS, ticketId, "in-review");

        
        return 0;
    }

    public List<Ticket> findAll() {
        String sql = "SELECT * FROM tickets";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ticket.class));
    }
    
    public List<TicketWithStatus> findByStatus(String status) {
    	String sql = "SELECT t.id,\r\n"
    			+ "       t.event_name,\r\n"
    			+ "       t.event_date_time,\r\n"
    			+ "       t.venue,\r\n"
    			+ "       t.price,\r\n"
    			+ "       t.file_path,\r\n"
    			+ "       t.event_image_path,\r\n"
    			+ "       t.uploaded_date_time,\r\n"
    			+ "       tr.status,\r\n"
    			+ "       tr.updated_at\r\n"
    			+ "FROM tickets t\r\n"
    			+ "INNER JOIN transactions tr ON t.id = tr.ticket_id\r\n"
    			+ "WHERE tr.status = ?;";
    	return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TicketWithStatus.class), status);
    }

    public Ticket findById(Long id) {
        String sql = "SELECT * FROM tickets WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Ticket.class), id);
    }
    
    public List<Ticket> findByUserEmail(String userEmail) {
    	String sql = "SELECT * FROM tickets WHERE uploaded_email = ?";
    	return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ticket.class), userEmail);
    }
}