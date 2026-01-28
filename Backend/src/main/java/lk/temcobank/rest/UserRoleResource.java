package lk.temcobank.rest;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lk.temcobank.dto.PageResponse;
import lk.temcobank.dto.UserRoleDTO;
import lk.temcobank.service.UserRoleService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User Roles", description = "Role management operations")
public class UserRoleResource {

    @Inject
    private UserRoleService roleService;

    @GET
    @Operation(summary = "Get all roles", description = "Retrieve paginated list of user roles")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "List of roles")
    })
    public Response getAllRoles(
            @Parameter(description = "Page number (0-based)") @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Page size") @QueryParam("size") @DefaultValue("20") int size,
            @Parameter(description = "Search term") @QueryParam("search") String search) {
        
        PageResponse<UserRoleDTO> response = roleService.findAll(page, size, search);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get role by ID", description = "Retrieve a specific role by its ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Role details"),
        @APIResponse(responseCode = "404", description = "Role not found")
    })
    public Response getRoleById(@PathParam("id") Long id) {
        try {
            UserRoleDTO role = roleService.findById(id);
            return Response.ok(role).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @POST
    @Operation(summary = "Create role", description = "Create a new user role")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "Role created"),
        @APIResponse(responseCode = "400", description = "Invalid input")
    })
    public Response createRole(@Valid UserRoleDTO dto) {
        UserRoleDTO created = roleService.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update role", description = "Update an existing role")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Role updated"),
        @APIResponse(responseCode = "404", description = "Role not found")
    })
    public Response updateRole(@PathParam("id") Long id, @Valid UserRoleDTO dto) {
        try {
            UserRoleDTO updated = roleService.update(id, dto);
            return Response.ok(updated).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete role", description = "Soft delete a role")
    @APIResponses({
        @APIResponse(responseCode = "204", description = "Role deleted"),
        @APIResponse(responseCode = "404", description = "Role not found")
    })
    public Response deleteRole(@PathParam("id") Long id) {
        try {
            roleService.delete(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
