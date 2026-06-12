package com.shivansh.employeemanagement.exception;

import com.shivansh.employeemanagement.dto.ApiResponse;
import com.shivansh.employeemanagement.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
            EmployeeTerminatedException.class)
    public ResponseEntity<ApiResponse<Employee>>
    handleEmployeeTerminated(
            EmployeeTerminatedException ex) {
        ApiResponse<Employee> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        ex.getEmployee());

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>>
    handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors =
                new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()));

        ApiResponse<Map<String, String>> response =
                new ApiResponse<>(
                        false,
                        "Validation failed",
                        errors);

        return ResponseEntity.badRequest()
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>>
    handleGenericException(Exception ex) {

        ApiResponse<Void> response =
                new ApiResponse<>(
                        false,
                        "Something went wrong",
                        null);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(
            BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>>
    handleBadRequest(
            BadRequestException ex) {

        ApiResponse<Void> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}