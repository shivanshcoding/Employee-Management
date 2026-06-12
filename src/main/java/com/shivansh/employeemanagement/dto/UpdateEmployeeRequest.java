package com.shivansh.employeemanagement.dto;

import com.shivansh.employeemanagement.entity.Department;
import com.shivansh.employeemanagement.entity.EmploymentStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployeeRequest {
    private String firstName;

    private String middleName;

    private String lastName;

    @Email(message = "Please provide a valid personal email")
    private String privateEmail;

    @Email(message = "Please provide a valid company email")
    private String companyEmail;

    private Department department;

    private EmploymentStatus status;

    private String designation;

    @Positive(message = "Salary must be greater than zero")
    private Double salary;

    private String phoneNumber;
}