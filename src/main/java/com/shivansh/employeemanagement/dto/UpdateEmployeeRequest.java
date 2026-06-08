package com.shivansh.employeemanagement.dto;

import com.shivansh.employeemanagement.entity.Department;
import com.shivansh.employeemanagement.entity.EmploymentStatus;

public class UpdateEmployeeRequest {

    private String firstName;

    private String middleName;

    private String lastName;

    private String privateEmail;

    private String companyEmail;

    private Department department;

    private EmploymentStatus status;

    private String designation;

    private Double salary;

    private String phoneNumber;

    // getters setters
}