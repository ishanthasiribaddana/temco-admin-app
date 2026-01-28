package lk.temcobank.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lk.temcobank.dto.MemberDTO;
import lk.temcobank.service.MemberService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/members")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemberResource {

    @EJB
    private MemberService memberService;

    @GET
    public Response getMembers(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("search") String search) {
        
        try {
            List<MemberDTO> members = memberService.getMembers(page, size, search);
            long total = memberService.getMemberCount(search);

            Map<String, Object> response = new HashMap<>();
            response.put("content", members);
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
