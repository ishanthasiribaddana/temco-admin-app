package lk.temcobank.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lk.temcobank.dto.UserDTO;
import lk.temcobank.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @EJB
    private UserService userService;

    @GET
    public Response getUsers(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("search") String search,
            @QueryParam("status") String status) {
        
        try {
            List<UserDTO> users = userService.getUsers(page, size, search, status);
            long total = userService.getUserCount(search, status);

            Map<String, Object> response = new HashMap<>();
            response.put("content", users);
            response.put("page", page);
            response.put("size", size);
            response.put("totalElements", total);
            response.put("totalPages", (int) Math.ceil((double) total / size));

            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }
}
