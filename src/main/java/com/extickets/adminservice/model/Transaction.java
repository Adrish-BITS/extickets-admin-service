package com.extickets.adminservice.model;

import java.time.LocalDateTime;

public class Transaction {
	private Long transactionId;
    private Long ticketId;          // Foreign key reference to Ticket
    private String status;          // e.g., "in-review", "approved", "sold"
    private LocalDateTime updatedAt;
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
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
    
}
