package com.shivansh.employeemanagement.entity;

import com.shivansh.employeemanagement.dto.CreateEmployeeRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID employeeId;

    private String employeeCode;

    private String firstName;

    private String middleName;

    private String lastName;

    private String privateEmail;

    private String companyEmail;

    private Department department;

    private EmploymentStatus status;

    private String designation;

    private String phoneNumber;

    private Double salary;

    private LocalDate joiningDate;

    private LocalDate exitDate;

    public Employee() {
    }

    public String getFullName() {
        if (middleName == null || middleName.isBlank()) {
            return firstName + " " + lastName;
        }

        return firstName + " " +
                middleName + " " +
                lastName;
    }

    public Employee(CreateEmployeeRequest request) {
        this.employeeCode = request.getEmployeeCode();
        this.firstName = request.getFirstName();
        this.middleName = request.getMiddleName();
        this.lastName = request.getLastName();
        this.companyEmail = request.getCompanyEmail();
        this.privateEmail = request.getPrivateEmail();
        this.department = request.getDepartment();
        this.status = EmploymentStatus.ACTIVE;
        this.designation = request.getDesignation();
        this.salary = request.getSalary();
        this.phoneNumber = request.getPhoneNumber();
        this.joiningDate = request.getJoiningDate();
        this.exitDate = null;
    }
}