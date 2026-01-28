package lk.temcobank.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

/**
 * JAX-RS Application configuration.
 */
@ApplicationPath("/api/v1")
@OpenAPIDefinition(
    info = @Info(
        title = "TEMCO Bank Student Loan API",
        version = "2.1.0",
        description = "REST API for TEMCO Bank Student Loan Management System",
        contact = @Contact(
            name = "TEMCO Bank Support",
            email = "admin@temcobank.lk",
            url = "https://temcobank.lk"
        ),
        license = @License(
            name = "Proprietary",
            url = "https://temcobank.lk/license"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080/temco-bank", description = "Development Server"),
        @Server(url = "https://api.temcobank.lk", description = "Production Server")
    }
)
public class JaxRsApplication extends Application {
    // JAX-RS will automatically discover all @Path annotated classes
}
