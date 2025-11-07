package com.extickets.adminservice.model;

import java.time.LocalDateTime;

public class TicketWithStatus extends Ticket{

	private String status;
	private LocalDateTime updatedAt;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public TicketWithStatus(String status, LocalDateTime updatedAt) {
		super();
		this.status = status;
		this.updatedAt = updatedAt;
	}
	
}
