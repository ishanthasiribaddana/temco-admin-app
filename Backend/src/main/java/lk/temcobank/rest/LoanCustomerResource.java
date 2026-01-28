package lk.temcobank.rest;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lk.temcobank.dto.LoanCustomerDTO;
import lk.temcobank.dto.PageResponse;
import lk.temcobank.service.LoanCustomerService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

/**
 * REST Resource for LoanCustomer operations.
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Customers", description = "Student/Customer management operations")
public class LoanCustomerResource {

    @Inject
    private LoanCustomerService customerService;

    // ==================== CRUD Endpoints ====================

    @POST
    @Operation(summary = "Create a new customer", description = "Creates a new student/customer record")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "Customer created successfully",
                content = @Content(schema = @Schema(implementation = LoanCustomerDTO.class))),
        @APIResponse(responseCode = "400", description = "Invalid input"),
        @APIResponse(responseCode = "409", description = "Customer already exists")
    })
    public Response create(@Valid LoanCustomerDTO dto) {
        LoanCustomerDTO created = customerService.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update customer", description = "Updates an existing customer record")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Customer updated successfully",
                content = @Content(schema = @Schema(implementation = LoanCustomerDTO.class))),
        @APIResponse(responseCode = "400", description = "Invalid input"),
        @APIResponse(responseCode = "404", description = "Customer not found")
    })
    public Response update(
            @Parameter(description = "Customer ID", required = true) @PathParam("id") Long id,
            @Valid LoanCustomerDTO dto) {
        LoanCustomerDTO updated = customerService.update(id, dto);
        return Response.ok(updated).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get customer by ID", description = "Retrieves a customer by their ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Customer found",
                content = @Content(schema = @Schema(implementation = LoanCustomerDTO.class))),
        @APIResponse(responseCode = "404", description = "Customer not found")
    })
    public Response findById(
            @Parameter(description = "Customer ID", required = true) @PathParam("id") Long id) {
        LoanCustomerDTO customer = customerService.findById(id);
        return Response.ok(customer).build();
    }

    @GET
    @Path("/student-id/{studentId}")
    @Operation(summary = "Get customer by student ID", description = "Retrieves a customer by their student ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Customer found",
                content = @Content(schema = @Schema(implementation = LoanCustomerDTO.class))),
        @APIResponse(responseCode = "404", description = "Customer not found")
    })
    public Response findByStudentId(
            @Parameter(description = "Student ID", required = true) @PathParam("studentId") String studentId) {
        LoanCustomerDTO customer = customerService.findByStudentId(studentId);
        return Response.ok(customer).build();
    }

    @GET
    @Path("/nic/{nic}")
    @Operation(summary = "Get customer by NIC", description = "Retrieves a customer by their NIC")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Customer found",
                content = @Content(schema = @Schema(implementation = LoanCustomerDTO.class))),
        @APIResponse(responseCode = "404", description = "Customer not found")
    })
    public Response findByNic(
            @Parameter(description = "NIC", required = true) @PathParam("nic") String nic) {
        LoanCustomerDTO customer = customerService.findByNic(nic);
        return Response.ok(customer).build();
    }

    @GET
    @Operation(summary = "Get all customers", description = "Retrieves all customers with optional pagination")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Customers retrieved successfully")
    })
    public Response findAll(
            @Parameter(description = "Page number (0-based)") @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Page size") @QueryParam("size") @DefaultValue("20") int size) {
        if (size > 0) {
            PageResponse<LoanCustomerDTO> response = customerService.findAll(page, size);
            return Response.ok(response).build();
        } else {
            List<LoanCustomerDTO> customers = customerService.findAll();
            return Response.ok(customers).build();
        }
    }

    @GET
    @Path("/search")
    @Operation(summary = "Search customers", description = "Search customers by keyword")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Search results")
    })
    public Response search(
            @Parameter(description = "Search keyword", required = true) @QueryParam("q") String keyword,
            @Parameter(description = "Page number") @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Page size") @QueryParam("size") @DefaultValue("20") int size) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Search keyword is required\"}")
                    .build();
        }
        PageResponse<LoanCustomerDTO> response = customerService.search(keyword, page, size);
        return Response.ok(response).build();
    }

    @GET
    @Path("/active")
    @Operation(summary = "Get active customers", description = "Retrieves all active customers")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Active customers retrieved")
    })
    public Response findActive() {
        List<LoanCustomerDTO> customers = customerService.findActive();
        return Response.ok(customers).build();
    }

    @GET
    @Path("/outstanding")
    @Operation(summary = "Get customers with outstanding payments", description = "Retrieves customers who have outstanding payment dues")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Customers with outstanding payments")
    })
    public Response findWithOutstandingPayments() {
        List<LoanCustomerDTO> customers = customerService.findWithOutstandingPayments();
        return Response.ok(customers).build();
    }

    @GET
    @Path("/overdue")
    @Operation(summary = "Get customers with overdue payments", description = "Retrieves customers who have overdue payment dues")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Customers with overdue payments")
    })
    public Response findWithOverduePayments() {
        List<LoanCustomerDTO> customers = customerService.findWithOverduePayments();
        return Response.ok(customers).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete customer", description = "Soft deletes a customer")
    @APIResponses({
        @APIResponse(responseCode = "204", description = "Customer deleted"),
        @APIResponse(responseCode = "404", description = "Customer not found")
    })
    public Response delete(
            @Parameter(description = "Customer ID", required = true) @PathParam("id") Long id) {
        customerService.delete(id);
        return Response.noContent().build();
    }

    // ==================== Status Management ====================

    @PUT
    @Path("/{id}/activate")
    @Operation(summary = "Activate customer", description = "Activates a customer account")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Customer activated"),
        @APIResponse(responseCode = "404", description = "Customer not found")
    })
    public Response activate(
            @Parameter(description = "Customer ID", required = true) @PathParam("id") Long id) {
        LoanCustomerDTO customer = customerService.activate(id);
        return Response.ok(customer).build();
    }

    @PUT
    @Path("/{id}/suspend")
    @Operation(summary = "Suspend customer", description = "Suspends a customer account")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Customer suspended"),
        @APIResponse(responseCode = "404", description = "Customer not found")
    })
    public Response suspend(
            @Parameter(description = "Customer ID", required = true) @PathParam("id") Long id) {
        LoanCustomerDTO customer = customerService.suspend(id);
        return Response.ok(customer).build();
    }
}
