package lk.temcobank.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lk.temcobank.dto.AuthRequest;
import lk.temcobank.dto.AuthResponse;
import lk.temcobank.service.AuthenticationService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * REST Resource for authentication operations.
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication", description = "Login, logout, and token management")
public class AuthResource {

    @Inject
    private AuthenticationService authService;

    @Context
    private HttpServletRequest httpRequest;

    @POST
    @Path("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT tokens")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Login successful"),
        @APIResponse(responseCode = "401", description = "Invalid credentials"),
        @APIResponse(responseCode = "403", description = "Account locked or inactive")
    })
    public Response login(@Valid AuthRequest request) {
        String ipAddress = getClientIpAddress();
        String userAgent = httpRequest.getHeader("User-Agent");
        
        AuthResponse response = authService.login(request, ipAddress, userAgent);
        return Response.ok(response).build();
    }

    @POST
    @Path("/logout")
    @Operation(summary = "User logout", description = "Invalidate user session")
    @APIResponses({
        @APIResponse(responseCode = "204", description = "Logout successful")
    })
    public Response logout(@HeaderParam("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            authService.logout(token);
        }
        return Response.noContent().build();
    }

    @POST
    @Path("/refresh")
    @Operation(summary = "Refresh token", description = "Get new access token using refresh token")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Token refreshed"),
        @APIResponse(responseCode = "401", description = "Invalid refresh token")
    })
    public Response refreshToken(@HeaderParam("X-Refresh-Token") String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Refresh token is required\"}")
                    .build();
        }
        AuthResponse response = authService.refreshToken(refreshToken);
        return Response.ok(response).build();
    }

    @POST
    @Path("/change-password")
    @Operation(summary = "Change password", description = "Change user password")
    @APIResponses({
        @APIResponse(responseCode = "204", description = "Password changed"),
        @APIResponse(responseCode = "400", description = "Invalid current password")
    })
    public Response changePassword(
            @HeaderParam("Authorization") String authHeader,
            ChangePasswordRequest request) {
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        String token = authHeader.substring(7);
        Long userId = authService.getCurrentUser(token).getUserId();
        
        authService.changePassword(userId, request.oldPassword, request.newPassword);
        return Response.noContent().build();
    }

    @GET
    @Path("/me")
    @Operation(summary = "Get current user", description = "Get current authenticated user details")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "User details"),
        @APIResponse(responseCode = "401", description = "Not authenticated")
    })
    public Response getCurrentUser(@HeaderParam("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        String token = authHeader.substring(7);
        AuthResponse response = authService.getCurrentUser(token);
        return Response.ok(response).build();
    }

    private String getClientIpAddress() {
        String xForwardedFor = httpRequest.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return httpRequest.getRemoteAddr();
    }

    // Inner class for change password request
    public static class ChangePasswordRequest {
        public String oldPassword;
        public String newPassword;
    }
}
