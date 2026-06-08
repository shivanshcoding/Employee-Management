package com.shivansh.employeemanagement.dto;

import com.shivansh.employeemanagement.entity.Department;
import com.shivansh.employeemanagement.entity.EmploymentStatus;
import jakarta.validation.constraints.*;

public class CreateEmployeeRequest {

    @NotBlank(message = "Employee code is required")
    private String employeeCode;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Personal email is required")
    @Email(message = "Please provide a valid personal email")
    private String privateEmail;

    @NotBlank(message = "Company email is required")
    @Email(message = "Please provide a valid company email")
    private String companyEmail;

    @NotNull(message = "Department is required")
    private Department department;

    @NotNull(message = "Status is required")
    private EmploymentStatus status;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than zero")
    private Double salary;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    // getters setters
}
