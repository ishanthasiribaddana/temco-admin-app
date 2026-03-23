package lk.temcobank.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lk.temcobank.dto.ContactMessageDTO;
import lk.temcobank.service.AuthenticationService;
import lk.temcobank.service.ContactMessageService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Resource for contact message operations.
 */
@Path("/contact-messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Contact Messages", description = "Send and manage contact messages")
public class ContactMessageResource {

    @Inject
    private ContactMessageService messageService;

    @Inject
    private AuthenticationService authService;

    /**
     * Submit a new contact message (authenticated users only).
     * POST /api/v1/contact-messages
     */
    @POST
    @Operation(summary = "Send contact message", description = "Submit a new contact message (requires authentication)")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "Message sent successfully"),
        @APIResponse(responseCode = "401", description = "Not authenticated"),
        @APIResponse(responseCode = "400", description = "Invalid request")
    })
    public Response create(
            @HeaderParam("Authorization") String authHeader,
            ContactMessageDTO.CreateRequest request) {

        Long userId = extractUserId(authHeader);

        if (request.getSubject() == null || request.getSubject().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Subject is required"))
                    .build();
        }
        if (request.getMessage() == null || request.getMessage().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Message is required"))
                    .build();
        }

        ContactMessageDTO created = messageService.create(userId, request);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Your message has been sent successfully");
        result.put("data", created);
        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    /**
     * Get all contact messages (admin only).
     * GET /api/v1/contact-messages
     */
    @GET
    @Operation(summary = "List all messages", description = "Get all contact messages (admin)")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Messages retrieved"),
        @APIResponse(responseCode = "401", description = "Not authenticated")
    })
    public Response findAll(
            @HeaderParam("Authorization") String authHeader,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {

        extractUserId(authHeader); // verify auth

        List<ContactMessageDTO> messages = messageService.findAll(page, size);
        long unreadCount = messageService.countUnread();

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", messages);
        result.put("unreadCount", unreadCount);
        return Response.ok(result).build();
    }

    /**
     * Get unread message count (admin).
     * GET /api/v1/contact-messages/unread-count
     */
    @GET
    @Path("/unread-count")
    @Operation(summary = "Get unread count", description = "Get count of unread messages")
    public Response getUnreadCount(@HeaderParam("Authorization") String authHeader) {
        extractUserId(authHeader);
        long count = messageService.countUnread();
        return Response.ok(Map.of("unreadCount", count)).build();
    }

    /**
     * Get a single message by ID.
     * GET /api/v1/contact-messages/{id}
     */
    @GET
    @Path("/{id}")
    @Operation(summary = "Get message", description = "Get a single contact message by ID")
    public Response findById(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("id") Long id) {

        extractUserId(authHeader);
        ContactMessageDTO message = messageService.findById(id);
        return Response.ok(Map.of("success", true, "data", message)).build();
    }

    /**
     * Mark a message as read.
     * PUT /api/v1/contact-messages/{id}/read
     */
    @PUT
    @Path("/{id}/read")
    @Operation(summary = "Mark as read", description = "Mark a contact message as read")
    public Response markAsRead(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("id") Long id) {

        extractUserId(authHeader);
        messageService.markAsRead(id);
        return Response.ok(Map.of("success", true)).build();
    }

    /**
     * Mark all messages as read.
     * PUT /api/v1/contact-messages/read-all
     */
    @PUT
    @Path("/read-all")
    @Operation(summary = "Mark all as read", description = "Mark all contact messages as read")
    public Response markAllAsRead(@HeaderParam("Authorization") String authHeader) {
        extractUserId(authHeader);
        int updated = messageService.markAllAsRead();
        return Response.ok(Map.of("success", true, "updated", updated)).build();
    }

    /**
     * Delete a message (soft delete).
     * DELETE /api/v1/contact-messages/{id}
     */
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete message", description = "Soft delete a contact message")
    public Response delete(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("id") Long id) {

        extractUserId(authHeader);
        messageService.delete(id);
        return Response.noContent().build();
    }

    // ==================== Helper ====================

    private Long extractUserId(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new WebApplicationException(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(Map.of("error", "Authentication required"))
                            .build());
        }
        String token = authHeader.substring(7);
        return authService.getCurrentUser(token).getUserId();
    }
}
