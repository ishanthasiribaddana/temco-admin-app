package lk.temcobank.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lk.temcobank.dto.EmailConfigDTO;
import lk.temcobank.dto.EmailRequestDTO;
import lk.temcobank.dto.MemberDTO;
import lk.temcobank.service.EmailService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/email")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmailResource {

    @EJB
    private EmailService emailService;

    @GET
    @Path("/config")
    public Response getConfig() {
        EmailConfigDTO config = emailService.getDefaultConfig();
        return Response.ok(config).build();
    }

    @POST
    @Path("/test")
    public Response testConnection(EmailConfigDTO config) {
        boolean success = emailService.testConnection(config);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "Connection successful" : "Connection failed");
        return Response.ok(result).build();
    }

    @POST
    @Path("/send")
    public Response sendEmail(EmailRequestDTO request) {
        try {
            List<MemberDTO> members;
            if (request.isSendToAll()) {
                members = emailService.getMembersWithEmail(null);
            } else {
                members = emailService.getMembersWithEmail(request.getMemberIds());
            }

            if (members.isEmpty()) {
                Map<String, Object> result = new HashMap<>();
                result.put("success", false);
                result.put("message", "No members with valid email addresses found");
                return Response.ok(result).build();
            }

            Map<String, Object> result = emailService.sendBulkEmail(
                members,
                request.getSubject(),
                request.getBody()
            );
            return Response.ok(result).build();
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @GET
    @Path("/members")
    public Response getMembersWithEmail() {
        List<MemberDTO> members = emailService.getMembersWithEmail(null);
        Map<String, Object> result = new HashMap<>();
        result.put("content", members);
        result.put("totalElements", members.size());
        return Response.ok(result).build();
    }

    @GET
    @Path("/templates")
    public Response getTemplates() {
        // Return predefined templates
        List<Map<String, String>> templates = List.of(
            Map.of(
                "id", "welcome",
                "name", "Welcome Email",
                "subject", "Welcome to TEMCO Bank",
                "body", "<h2>Welcome {{fullName}}!</h2><p>Thank you for joining TEMCO Bank. Your membership number is <strong>{{membershipNo}}</strong>.</p><p>We're excited to have you as a member.</p><p>Best regards,<br>TEMCO Bank Team</p>"
            ),
            Map.of(
                "id", "loan_offer",
                "name", "Loan Offer",
                "subject", "Special Loan Offer for You",
                "body", "<h2>Dear {{fullName}},</h2><p>We have a special loan offer for you!</p><p>As a valued member ({{membershipNo}}), you're eligible for our low-interest loans.</p><p>Contact us to learn more.</p><p>Best regards,<br>TEMCO Bank Team</p>"
            ),
            Map.of(
                "id", "payment_reminder",
                "name", "Payment Reminder",
                "subject", "Payment Reminder",
                "body", "<h2>Dear {{fullName}},</h2><p>This is a friendly reminder about your upcoming payment.</p><p>Please ensure your account is funded to avoid any late fees.</p><p>Thank you,<br>TEMCO Bank Team</p>"
            ),
            Map.of(
                "id", "newsletter",
                "name", "Monthly Newsletter",
                "subject", "TEMCO Bank Newsletter",
                "body", "<h2>Hello {{fullName}},</h2><p>Here's what's new at TEMCO Bank this month!</p><p>Stay tuned for exciting updates and offers.</p><p>Best regards,<br>TEMCO Bank Team</p>"
            ),
            Map.of(
                "id", "custom",
                "name", "Custom Email",
                "subject", "",
                "body", ""
            )
        );
        return Response.ok(templates).build();
    }
}
