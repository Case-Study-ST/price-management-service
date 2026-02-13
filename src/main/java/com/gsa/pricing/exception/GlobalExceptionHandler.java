package com.gsa.pricing.exception;

import com.gsa.common.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PricingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePricingNotFound(
            PricingNotFoundException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.NOT_FOUND,
                "PRICING_NOT_FOUND",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildError(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                message,
                request
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.BAD_REQUEST,
                "INVALID_REQUEST",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.CONFLICT,
                "DATA_CONFLICT",
                "Pricing already exists for this show and seat type",
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                ex.getMessage(),
                request
        );
    }

    private ResponseEntity<ErrorResponse> buildError(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, status);
    }
}
