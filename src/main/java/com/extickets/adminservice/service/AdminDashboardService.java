package com.extickets.adminservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.extickets.adminservice.model.Ticket;
import com.extickets.adminservice.model.TicketWithStatus;
import com.extickets.adminservice.repository.AdminDashboardRepository;
import com.extickets.adminservice.repository.UploadTicketRepository;

@Service
public class AdminDashboardService {

	@Autowired
	private UploadTicketRepository ticketRepository;

	@Autowired
	private AdminDashboardRepository adminRepository;

	public void saveTicket(Ticket ticket) {
		ticketRepository.save(ticket);
	}

	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

	public List<TicketWithStatus> getAllTicketsBasedOnStatus(String status) {
		return ticketRepository.findByStatus(status);
	}

	public boolean changeStatus(Long ticketId, String user, String status, String comments) {
		return adminRepository.changeStatus(ticketId, user, status, comments);
	}
	
	public Ticket getTicketById(Long id) {
		return ticketRepository.findById(id);
	}

}