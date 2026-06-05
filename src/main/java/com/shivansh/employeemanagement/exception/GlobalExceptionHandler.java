package com.shivansh.employeemanagement.exception;

import com.shivansh.employeemanagement.entity.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            EmployeeNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>>
    handleEmployeeNotFound(
            EmployeeNotFoundException ex) {

        ApiResponse<Void> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(
            DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>>
    handleDuplicateResource(
            DuplicateResourceException ex) {

        ApiResponse<Void> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null);

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

}