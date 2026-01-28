package lk.temcobank.rest.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * CORS filter for cross-origin requests from React frontend.
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, 
                       ContainerResponseContext responseContext) throws IOException {
        
        // Allow requests from React development server and production
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        
        // Allow credentials
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        
        // Allowed headers
        responseContext.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization, x-requested-with");
        
        // Allowed methods
        responseContext.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
        
        // Max age for preflight cache
        responseContext.getHeaders().add("Access-Control-Max-Age", "86400");
        
        // Expose headers to client
        responseContext.getHeaders().add("Access-Control-Expose-Headers",
                "Content-Disposition, X-Total-Count, X-Page-Number, X-Page-Size");
    }
}
