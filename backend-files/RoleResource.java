package com.temco.resource;

import com.temco.dto.PagedResponse;
import com.temco.dto.RoleDTO;
import com.temco.service.RoleService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoleResource {

    @Inject
    private RoleService roleService;

    @GET
    public Response getAllRoles(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("100") int size) {
        PagedResponse<RoleDTO> roles = roleService.getRolesPaginated(page, size);
        return Response.ok(roles).build();
    }

    @GET
    @Path("/{id}")
    public Response getRoleById(@PathParam("id") Integer id) {
        RoleDTO role = roleService.getRoleById(id);
        if (role == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(role).build();
    }
}
