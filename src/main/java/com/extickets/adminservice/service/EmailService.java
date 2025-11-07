package com.extickets.adminservice.service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.extickets.adminservice.enums.AdminEmailTemplates;
import com.extickets.adminservice.enums.UserEmailTemplates;
import com.extickets.adminservice.model.Ticket;

@Service
public class EmailService {

//	@Autowired
//	private JavaMailSender mailSender;
	
    @Autowired
    private RestTemplate restTemplate; 

	public void sendEmail(Ticket ticket, String newStatus) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
		String eventImageUploadUrl = ticket.getEventImagePath();
		if (newStatus.equalsIgnoreCase("Approved")) {
			String userHtmlBody = UserEmailTemplates.TICKET_APPROVED.getTemplate()
					.replace("${eventName}", ticket.getEventName()).replace("${venue}", ticket.getVenue())
					.replace("${eventDateTime}", ticket.getEventDateTime().format(formatter).toString())
					.replace("${price}", ticket.getPrice().toString()).replace("${status}", "Review");
			// TODO Add user email id here
			sendRequestToEmailService("2023tm93518@wilp.bits-pilani.ac.in",
					"[ExTickets] Yayy!!! Your ticket is approved and live now", userHtmlBody);

			String adminHtmlBody = AdminEmailTemplates.ADMIN_APPROVED.getTemplate()
					.replace("${eventName}", ticket.getEventName()).replace("${venue}", ticket.getVenue())
					.replace("${eventDateTime}", ticket.getEventDateTime().format(formatter).toString())
					.replace("${price}", ticket.getPrice().toString()).replace("${status}", "Review");
			// TODO Add admin email id here
			sendRequestToEmailService("backuponeplus345@gmail.com", "[ExTickets] You Approved a Ticket Successfully",
					adminHtmlBody);
		} else if (newStatus.equalsIgnoreCase("Rejected")) {
			String userHtmlBody = UserEmailTemplates.TICKET_REJECTED.getTemplate()
					.replace("${eventName}", ticket.getEventName()).replace("${venue}", ticket.getVenue())
					.replace("${eventDateTime}", ticket.getEventDateTime().format(formatter).toString())
					.replace("${price}", ticket.getPrice().toString()).replace("${status}", "Review");
			// TODO Add user email id here
			sendRequestToEmailService("2023tm93518@wilp.bits-pilani.ac.in",
					"[ExTickets] Oops!!! Your ticket is rejected", userHtmlBody);

			String adminHtmlBody = AdminEmailTemplates.ADMIN_REJECTED.getTemplate()
					.replace("${eventName}", ticket.getEventName()).replace("${venue}", ticket.getVenue())
					.replace("${eventDateTime}", ticket.getEventDateTime().format(formatter).toString())
					.replace("${price}", ticket.getPrice().toString()).replace("${status}", "Review");
			// TODO Add admin email id here
			sendRequestToEmailService("backuponeplus345@gmail.com", "[ExTickets] You Rejected a Ticket Successfully",
					adminHtmlBody);
		}
//		String userHtmlBody = UserEmailTemplates.TICKET_UPLOADED.getTemplate().replace("${eventName}", ticket.getEventName())
//				.replace("${eventImageUploadUrl}", eventImageUploadUrl)
//				.replace("${venue}", ticket.getVenue())
//				.replace("${eventDateTime}", ticket.getEventDateTime().format(formatter).toString())
//				.replace("${price}", ticket.getPrice().toString()).replace("${status}", "Review");
//		//TODO Add user email id here
//		sendRequestToEmailService("2023tm93518@wilp.bits-pilani.ac.in", "[ExTickets] Tickets Uploaded successfully", userHtmlBody);
//		
//		String adminHtmlBody = AdminEmailTemplates.USER_UPLOADED.getTemplate()
//				.replace("${eventName}", ticket.getEventName())
//				.replace("${venue}", ticket.getVenue())
//				.replace("${eventDateTime}", ticket.getEventDateTime().format(formatter).toString())
//				.replace("${price}", ticket.getPrice().toString()).replace("${status}", "Review");
//		//TODO Add admin email id here
//		sendRequestToEmailService("backuponeplus345@gmail.com", "[ExTickets] New Ticket Uploaded - Review Required", adminHtmlBody);
	}

	private void sendRequestToEmailService(String to, String subject, String body) {
		String emailServiceUrl = "http://localhost:8082/api/tickets/sendEmail";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> payload = new HashMap<>();
		payload.put("to", to);
		payload.put("subject", subject);
		payload.put("body", body);

		HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

		try {
			restTemplate.postForEntity(emailServiceUrl, request, String.class);
			System.out.println("Email request sent to email-service successfully.");
		} catch (Exception e) {
			System.err.println("Failed to send email request: " + e.getMessage());
		}
	}

}
