package com.extickets.adminservice.enums;


public enum AdminEmailTemplates {

    USER_UPLOADED("""
            <html>
              <body style="font-family:Segoe UI, sans-serif; background-color:#f4f4f4; padding:20px;">
                <div style="max-width:600px; margin:auto; background:#ffffff; border-radius:8px; padding:20px; 
                            box-shadow:0px 2px 6px rgba(0,0,0,0.1); font-size:15px;">
                  <h2 style="color:#002752; text-align:center;">📩 ExTickets - New Ticket Uploaded</h2>
                  <p>Dear Admin,</p>
                  <p>A user has uploaded a new ticket. Please review and take necessary action.</p>
                  <table style="width:100%; border-collapse:collapse; margin:20px 0; font-size:15px;">
                    <tr style="background-color:#002752; color:white;">
                      <th style="text-align:left; padding:10px;">Field</th>
                      <th style="text-align:left; padding:10px;">Details</th>
                    </tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">User</td><td style="padding:10px; border-bottom:1px solid #ddd;">${username}</td></tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">Event Name</td><td style="padding:10px; border-bottom:1px solid #ddd;">${eventName}</td></tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">Venue</td><td style="padding:10px; border-bottom:1px solid #ddd;">${venue}</td></tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">Date & Time</td><td style="padding:10px; border-bottom:1px solid #ddd;">${eventDateTime}</td></tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">Price</td><td style="padding:10px; border-bottom:1px solid #ddd;">₹${price}</td></tr>
                  </table>
                  <p style="margin-top:20px;">Login to the admin dashboard to approve or reject this ticket.</p>
                  <p style="font-size:12px; color:#888; text-align:center; margin-top:30px;">
                    This is an automated notification from ExTickets.
                  </p>
                </div>
              </body>
            </html>
        """),

        ADMIN_APPROVED("""
            <html>
              <body style="font-family:Segoe UI, sans-serif; background-color:#f4f4f4; padding:20px;">
                <div style="max-width:600px; margin:auto; background:#ffffff; border-radius:8px; padding:20px; 
                            box-shadow:0px 2px 6px rgba(0,0,0,0.1); font-size:15px;">
                  <h2 style="color:#28a745; text-align:center;">✅ ExTickets - Ticket Approved</h2>
                  <p>Dear Admin,</p>
                  <p>You have successfully <b style="color:#28a745;">approved</b> the following ticket:</p>
                  <table style="width:100%; border-collapse:collapse; margin:20px 0; font-size:15px;">
                    <tr style="background-color:#28a745; color:white;">
                      <th style="text-align:left; padding:10px;">Field</th>
                      <th style="text-align:left; padding:10px;">Details</th>
                    </tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">Event Name</td><td style="padding:10px; border-bottom:1px solid #ddd;">${eventName}</td></tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">Venue</td><td style="padding:10px; border-bottom:1px solid #ddd;">${venue}</td></tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">Date & Time</td><td style="padding:10px; border-bottom:1px solid #ddd;">${eventDateTime}</td></tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">Price</td><td style="padding:10px; border-bottom:1px solid #ddd;">₹${price}</td></tr>
                  </table>
                  <p style="margin-top:20px;">The ticket is now live on the platform.</p>
                  <p style="font-size:12px; color:#888; text-align:center; margin-top:30px;">
                    This is a confirmation email from ExTickets.
                  </p>
                </div>
              </body>
            </html>
        """),

        ADMIN_REJECTED("""
            <html>
              <body style="font-family:Segoe UI, sans-serif; background-color:#f4f4f4; padding:20px;">
                <div style="max-width:600px; margin:auto; background:#ffffff; border-radius:8px; padding:20px; 
                            box-shadow:0px 2px 6px rgba(0,0,0,0.1); font-size:15px;">
                  <h2 style="color:#e74c3c; text-align:center;">❌ ExTickets - Ticket Rejected</h2>
                  <p>Dear Admin,</p>
                  <p>You have <b style="color:#e74c3c;">rejected</b> the following ticket:</p>
                  <table style="width:100%; border-collapse:collapse; margin:20px 0; font-size:15px;">
                    <tr style="background-color:#e74c3c; color:white;">
                      <th style="text-align:left; padding:10px;">Field</th>
                      <th style="text-align:left; padding:10px;">Details</th>
                    </tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">Event Name</td><td style="padding:10px; border-bottom:1px solid #ddd;">${eventName}</td></tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">Venue</td><td style="padding:10px; border-bottom:1px solid #ddd;">${venue}</td></tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">Date & Time</td><td style="padding:10px; border-bottom:1px solid #ddd;">${eventDateTime}</td></tr>
                    <tr><td style="padding:10px; border-bottom:1px solid #ddd;">Price</td><td style="padding:10px; border-bottom:1px solid #ddd;">₹${price}</td></tr>
                  </table>
                  <p style="margin-top:20px;">The user has been notified of this rejection.</p>
                  <p style="font-size:12px; color:#888; text-align:center; margin-top:30px;">
                    This is a confirmation email from ExTickets.
                  </p>
                </div>
              </body>
            </html>
        """);

        private final String template;

        AdminEmailTemplates(String template) {
            this.template = template;
        }

        public String getTemplate() {
            return template;
        }
}
