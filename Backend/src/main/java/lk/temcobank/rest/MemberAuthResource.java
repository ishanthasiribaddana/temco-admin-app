package lk.temcobank.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lk.temcobank.service.MemberAuthService;

import java.util.HashMap;
import java.util.Map;

@Path("/member-auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemberAuthResource {

    @EJB
    private MemberAuthService memberAuthService;

    /**
     * Generate login accounts for all members with email
     * POST /api/v1/member-auth/generate-logins
     */
    @POST
    @Path("/generate-logins")
    public Response generateLogins() {
        try {
            int created = memberAuthService.generateMemberLogins();
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("created", created);
            result.put("message", created + " member login accounts created");
            return Response.ok(result).build();
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Get NIC hint for email (for login page)
     * GET /api/v1/member-auth/nic-hint?email=xxx
     */
    @GET
    @Path("/nic-hint")
    public Response getNicHint(@QueryParam("email") String email) {
        if (email == null || email.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Email is required"))
                    .build();
        }

        String hint = memberAuthService.getNicHint(email);
        if (hint != null) {
            return Response.ok(Map.of("hint", hint, "found", true)).build();
        } else {
            return Response.ok(Map.of("hint", "", "found", false)).build();
        }
    }

    /**
     * Check if user must change password
     * GET /api/v1/member-auth/must-change-password?username=xxx
     */
    @GET
    @Path("/must-change-password")
    public Response mustChangePassword(@QueryParam("username") String username) {
        boolean mustChange = memberAuthService.mustChangePassword(username);
        return Response.ok(Map.of("mustChangePassword", mustChange)).build();
    }

    /**
     * Change password (first login or regular change)
     * POST /api/v1/member-auth/change-password
     */
    @POST
    @Path("/change-password")
    public Response changePassword(ChangePasswordRequest request) {
        if (request.username == null || request.currentPassword == null || request.newPassword == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "All fields are required"))
                    .build();
        }

        boolean success = memberAuthService.changePassword(
                request.username, 
                request.currentPassword, 
                request.newPassword
        );

        if (success) {
            return Response.ok(Map.of("success", true, "message", "Password changed successfully")).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("success", false, "error", "Current password is incorrect"))
                    .build();
        }
    }

    public static class ChangePasswordRequest {
        public String username;
        public String currentPassword;
        public String newPassword;
    }
}
