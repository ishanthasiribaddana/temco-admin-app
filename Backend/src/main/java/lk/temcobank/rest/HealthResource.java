package lk.temcobank.rest;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Health check endpoint for Docker healthcheck and monitoring.
 */
@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    @PersistenceContext
    private EntityManager em;

    @GET
    public Response health() {
        try {
            // Verify DB connectivity
            em.createNativeQuery("SELECT 1").getSingleResult();

            return Response.ok(Map.of(
                    "status", "UP",
                    "timestamp", LocalDateTime.now().toString(),
                    "database", "connected"
            )).build();
        } catch (Exception e) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(Map.of(
                            "status", "DOWN",
                            "timestamp", LocalDateTime.now().toString(),
                            "database", "disconnected",
                            "error", e.getMessage()
                    )).build();
        }
    }
}
