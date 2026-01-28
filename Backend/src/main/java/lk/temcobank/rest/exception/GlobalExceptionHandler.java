package lk.temcobank.rest.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lk.temcobank.exception.BusinessException;
import lk.temcobank.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for REST API.
 */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public Response toResponse(Throwable exception) {
        logger.error("Exception caught: ", exception);

        if (exception instanceof ResourceNotFoundException) {
            return handleResourceNotFound((ResourceNotFoundException) exception);
        }

        if (exception instanceof BusinessException) {
            return handleBusinessException((BusinessException) exception);
        }

        if (exception instanceof ConstraintViolationException) {
            return handleValidationException((ConstraintViolationException) exception);
        }

        if (exception instanceof IllegalArgumentException) {
            return handleBadRequest(exception);
        }

        return handleGenericException(exception);
    }

    private Response handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> error = createErrorResponse(
                Response.Status.NOT_FOUND.getStatusCode(),
                "NOT_FOUND",
                ex.getMessage()
        );
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }

    private Response handleBusinessException(BusinessException ex) {
        Map<String, Object> error = createErrorResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                ex.getErrorCode() != null ? ex.getErrorCode() : "BUSINESS_ERROR",
                ex.getMessage()
        );
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }

    private Response handleValidationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        Map<String, Object> error = createErrorResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "VALIDATION_ERROR",
                message
        );

        // Add field-level errors
        Map<String, String> fieldErrors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            fieldErrors.put(field, violation.getMessage());
        }
        error.put("fieldErrors", fieldErrors);

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }

    private Response handleBadRequest(Throwable ex) {
        Map<String, Object> error = createErrorResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "BAD_REQUEST",
                ex.getMessage()
        );
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }

    private Response handleGenericException(Throwable ex) {
        Map<String, Object> error = createErrorResponse(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "INTERNAL_ERROR",
                "An unexpected error occurred. Please try again later."
        );
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }

    private Map<String, Object> createErrorResponse(int status, String code, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", status);
        error.put("error", code);
        error.put("message", message);
        return error;
    }
}
