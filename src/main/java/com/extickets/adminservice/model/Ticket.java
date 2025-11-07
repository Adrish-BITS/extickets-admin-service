package com.extickets.adminservice.model;

import java.time.LocalDateTime;

public class Ticket {

	private String id;
	private String eventName;
	private LocalDateTime eventDateTime;
	private String venue;
	private Double price;
	private String filePath;
	private String eventImagePath;
	private String uploadedDateTime;
	private String uploadedUserName;
	private String uploadedEmail;

	// Getters and setters

	public String getEventName() {
		return eventName;
	}

	public String getUploadedUserName() {
		return uploadedUserName;
	}

	public void setUploadedUserName(String uploadedUserName) {
		this.uploadedUserName = uploadedUserName;
	}

	public String getUploadedEmail() {
		return uploadedEmail;
	}

	public void setUploadedEmail(String uploadedEmail) {
		this.uploadedEmail = uploadedEmail;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public LocalDateTime getEventDateTime() {
		return eventDateTime;
	}

	public void setEventDateTime(LocalDateTime eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEventImagePath() {
		return eventImagePath;
	}

	public void setEventImagePath(String eventImagePath) {
		this.eventImagePath = eventImagePath;
	}

	public String getUploadedDateTime() {
		return uploadedDateTime;
	}

	public void setUploadedDateTime(String uploadedDateTime) {
		this.uploadedDateTime = uploadedDateTime;
	}

}
