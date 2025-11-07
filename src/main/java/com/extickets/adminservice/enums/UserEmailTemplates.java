package com.extickets.adminservice.enums;


public enum UserEmailTemplates {

	TICKET_UPLOADED("""
		    <html>
		      <body style="font-family: 'Segoe UI', Arial, sans-serif; background-color:#f4f4f4; padding:20px; font-size:15px;">
		        <div style="max-width:650px; margin:auto; background:#ffffff; border-radius:8px; padding:25px; box-shadow:0px 2px 8px rgba(0,0,0,0.12);">
		          <div style="text-align:center; margin-bottom:15px;">
		            <img src=${eventImageUploadUrl} alt="ExTickets Logo" style="width: 80px; height: auto;">
		          </div>

		          <h2 style="color:#002752; text-align:center; font-size:22px; margin-bottom:20px;">🎟️ ExTickets - Ticket Uploaded</h2>
		          <p style="font-size:15px;">Dear User,</p>
		          <p style="font-size:15px;">Your ticket has been <b style="color:#2E86C1;">uploaded successfully</b> and is under review.</p>
				  
		          <table style="width:100%; border-collapse:collapse; margin:20px 0; font-size:15px;">
		            <tr style="background-color:#002752; color:white;">
		              <th style="text-align:left; padding:12px;">Field</th>
		              <th style="text-align:left; padding:12px;">Details</th>
		            </tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Event Name</td><td style="padding:12px; border-bottom:1px solid #ddd;">${eventName}</td></tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Venue</td><td style="padding:12px; border-bottom:1px solid #ddd;">${venue}</td></tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Date & Time</td><td style="padding:12px; border-bottom:1px solid #ddd;">${eventDateTime}</td></tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Price</td><td style="padding:12px; border-bottom:1px solid #ddd;">₹${price}</td></tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Status</td><td style="padding:12px; border-bottom:1px solid #ddd;"><b style="color:#ff9800;">${status}</b></td></tr>
		          </table>

		          <p style="margin-top:20px; font-size:15px;">You will be notified once the admin reviews your ticket.</p>
		          <p style="font-size:12px; color:#888; text-align:center; margin-top:30px;">
		            This is an automated email from ExTickets. Please do not reply.
		          </p>
		        </div>
		      </body>
		    </html>
		"""),

	TICKET_APPROVED("""
		    <html>
		      <body style="font-family: 'Segoe UI', Arial, sans-serif; background-color:#f4f4f4; padding:20px; font-size:15px;">
		        <div style="max-width:650px; margin:auto; background:#ffffff; border-radius:8px; padding:25px; box-shadow:0px 2px 8px rgba(0,0,0,0.12);">
		          <h2 style="color:#28a745; text-align:center; font-size:22px; margin-bottom:20px;">✅ ExTickets - Ticket Approved</h2>
		          <p style="font-size:15px;">Dear User,</p>
		          <p style="font-size:15px;">Your ticket has been <b style="color:#28a745;">approved</b> and is now live.</p>
		          
		          <table style="width:100%; border-collapse:collapse; margin:20px 0; font-size:15px;">
		            <tr style="background-color:#28a745; color:white;">
		              <th style="text-align:left; padding:12px;">Field</th>
		              <th style="text-align:left; padding:12px;">Details</th>
		            </tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Event Name</td><td style="padding:12px; border-bottom:1px solid #ddd;">${eventName}</td></tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Venue</td><td style="padding:12px; border-bottom:1px solid #ddd;">${venue}</td></tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Date & Time</td><td style="padding:12px; border-bottom:1px solid #ddd;">${eventDateTime}</td></tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Price</td><td style="padding:12px; border-bottom:1px solid #ddd;">₹${price}</td></tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Status</td><td style="padding:12px; border-bottom:1px solid #ddd;"><b style="color:#28a745;">${status}</b></td></tr>
		          </table>

		          <p style="margin-top:20px; font-size:15px;">Your ticket is now available for buyers.</p>
		          <p style="font-size:12px; color:#888; text-align:center; margin-top:30px;">
		            This is an automated email from ExTickets. Please do not reply.
		          </p>
		        </div>
		      </body>
		    </html>
		"""),

	TICKET_REJECTED("""
		    <html>
		      <body style="font-family: 'Segoe UI', Arial, sans-serif; background-color:#f4f4f4; padding:20px; font-size:15px;">
		        <div style="max-width:650px; margin:auto; background:#ffffff; border-radius:8px; padding:25px; box-shadow:0px 2px 8px rgba(0,0,0,0.12);">
		          <h2 style="color:#e74c3c; text-align:center; font-size:22px; margin-bottom:20px;">❌ ExTickets - Ticket Rejected</h2>
		          <p style="font-size:15px;">Dear User,</p>
		          <p style="font-size:15px;">Unfortunately, your ticket has been <b style="color:#e74c3c;">rejected</b>.</p>
		          
		          <table style="width:100%; border-collapse:collapse; margin:20px 0; font-size:15px;">
		            <tr style="background-color:#e74c3c; color:white;">
		              <th style="text-align:left; padding:12px;">Field</th>
		              <th style="text-align:left; padding:12px;">Details</th>
		            </tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Event Name</td><td style="padding:12px; border-bottom:1px solid #ddd;">${eventName}</td></tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Venue</td><td style="padding:12px; border-bottom:1px solid #ddd;">${venue}</td></tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Date & Time</td><td style="padding:12px; border-bottom:1px solid #ddd;">${eventDateTime}</td></tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Price</td><td style="padding:12px; border-bottom:1px solid #ddd;">₹${price}</td></tr>
		            <tr><td style="padding:12px; border-bottom:1px solid #ddd;">Status</td><td style="padding:12px; border-bottom:1px solid #ddd;"><b style="color:#e74c3c;">${status}</b></td></tr>
		          </table>

		          <p style="margin-top:20px; font-size:15px;">You may re-upload your ticket with corrected details.</p>
		          <p style="font-size:12px; color:#888; text-align:center; margin-top:30px;">
		            This is an automated email from ExTickets. Please do not reply.
		          </p>
		        </div>
		      </body>
		    </html>
		""");


    private final String template;

    UserEmailTemplates(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
