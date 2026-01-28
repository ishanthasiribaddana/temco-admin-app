package lk.temcobank.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lk.temcobank.dto.ActivityLogDTO;
import lk.temcobank.service.ActivityLogService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/audit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActivityLogResource {

    @EJB
    private ActivityLogService activityLogService;

    /**
     * Get activity logs (login sessions)
     * GET /api/v1/audit/activity?page=0&size=20&search=xxx&action=LOGIN
     */
    @GET
    @Path("/activity")
    public Response getActivityLogs(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("search") String search,
            @QueryParam("action") String action) {
        
        try {
            List<ActivityLogDTO> logs = activityLogService.getActivityLogs(page, size, search, action);
            long total = activityLogService.getActivityLogCount(search);

            Map<String, Object> response = new HashMap<>();
            response.put("content", logs);
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

    /**
     * Get data change logs
     * GET /api/v1/audit/data-changes?page=0&size=20&search=xxx
     */
    @GET
    @Path("/data-changes")
    public Response getDataChangeLogs(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("search") String search) {
        
        try {
            List<ActivityLogDTO> logs = activityLogService.getDataChangeLogs(page, size, search);
            long total = activityLogService.getDataChangeLogCount(search);

            Map<String, Object> response = new HashMap<>();
            response.put("content", logs);
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
