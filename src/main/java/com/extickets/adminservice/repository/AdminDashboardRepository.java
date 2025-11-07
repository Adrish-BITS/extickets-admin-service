package com.extickets.adminservice.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.extickets.adminservice.model.Ticket;
import com.extickets.adminservice.model.TicketWithStatus;


@Repository
public class AdminDashboardRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Ticket> findAll() {
		String sql = "SELECT * FROM tickets";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ticket.class));
	}

	public List<TicketWithStatus> findByStatus(String status) {
		String sql =
				"""
				SELECT t.id,
				       t.event_name,
				       t.event_date_time,
				       t.venue,
				       t.price,
				       t.file_path,
				       t.event_image_path,
				       t.uploaded_date_time,
				       tr.status,
				       tr.updated_at
				FROM tickets t
				INNER JOIN transactions tr ON t.id = tr.ticket_id
				WHERE tr.status = ?
				""";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TicketWithStatus.class), status);
	}

	public boolean changeStatus(Long ticketId,String user, String status, String comments) {
		String sql = "UPDATE transactions SET status = ?, updated_by = ?, comments = ?, updated_at = CURRENT_TIMESTAMP " + "WHERE ticket_id = ?";
		return jdbcTemplate.update(sql, status, user, comments, ticketId)>0;
	}

}