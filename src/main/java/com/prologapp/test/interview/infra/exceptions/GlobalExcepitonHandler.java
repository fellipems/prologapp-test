package com.prologapp.test.interview.infra.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExcepitonHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> messages = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        String message = String.join("; ", messages);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.of(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        message
                    )
                );
    }

    @ExceptionHandler(BrandNotSpecifiedException.class)
    public ResponseEntity<ApiError> handleBrandNotSpecifiedException(BrandNotSpecifiedException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST).body(
                        ApiError.of(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ApiError> handleVehicleNotFoundException(VehicleNotFoundException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).body(
                        ApiError.of(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase(),
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(VehicleCreateTypeIsNotVehicleException.class)
    public ResponseEntity<ApiError> handleVehicleCreateTypeIsNotVehicleException(VehicleCreateTypeIsNotVehicleException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST).body(
                        ApiError.of(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(VehicleAlreadyExistsWithGivenPlateException.class)
    public ResponseEntity<ApiError> handleVehicleAlreadyExistsWithGivenPlateException(VehicleAlreadyExistsWithGivenPlateException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST).body(
                        ApiError.of(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNotFound(NoSuchElementException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiError.of(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        ex.getMessage()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiError.of(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        ex.getMessage()
                )
        );
    }
}
