package com.extickets.adminservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.extickets.adminservice.model.Ticket;
import com.extickets.adminservice.model.TicketWithStatus;
import com.extickets.adminservice.service.AdminDashboardService;
import com.extickets.adminservice.service.EmailService;

@RestController
@RequestMapping("/api/admin/tickets")
public class AdminDashboardController {

	@Autowired
	private AdminDashboardService adminService;

	@Autowired
	private EmailService emailService;

	@GetMapping("/status/{status}")
	public ResponseEntity<List<TicketWithStatus>> getAllTicketsBasedOnStatus(@PathVariable String status) {
		return ResponseEntity.ok(adminService.getAllTicketsBasedOnStatus(status));
	}

	@PostMapping("/ticket/{id}/changeStatus/{newStatus}/comments/{comments}")
	public ResponseEntity<String> changeStatus(@PathVariable Long id, @PathVariable String newStatus, @PathVariable String comments) {

		Ticket ticket = adminService.getTicketById(id);
		emailService.sendEmail(ticket, newStatus);

		return adminService.changeStatus(id, "ADMIN", newStatus, comments) ? ResponseEntity.ok("Ticket status changed successfully!")
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such ticket exists");
	}

}
