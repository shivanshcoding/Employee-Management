package com.shivansh.employeemanagement.controller;

import com.shivansh.employeemanagement.dto.ApiResponse;
import com.shivansh.employeemanagement.entity.Department;
import com.shivansh.employeemanagement.entity.Employee;
import com.shivansh.employeemanagement.entity.EmploymentStatus;
import com.shivansh.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(
            @Valid @RequestBody Employee employeeData) {

        Employee newEmployeeCreated = service.createEmployee(employeeData);
        ApiResponse<Employee> response =
                new ApiResponse<>(
                        true,
                        "Employee created successfully",
                        newEmployeeCreated);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees() {
        ApiResponse<List<Employee>> response =
                new ApiResponse<>(
                        true,
                        "Employee fetched successfully",
                        service.getAllEmployees());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetch/{employeeCode}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeByCode(
            @PathVariable String employeeCode) {
        ApiResponse<Employee> response =
                new ApiResponse<>(
                        true,
                        "Employee fetched successfully",
                        service.getEmployeeByCode(employeeCode));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetch")
    public ResponseEntity<ApiResponse<List<Employee>>> getEmployee(
            @RequestParam(required = false)
            String employeeCode,

            @RequestParam(required = false)
            String companyEmail,

            @RequestParam(required = false)
            String privateEmail,

            @RequestParam(required = false)
            Department department,

            @RequestParam(required = false)
            EmploymentStatus status
    ){

        List<Employee> fetchedEmployees =  service.fetchEmployees(
                employeeCode,
                companyEmail,
                privateEmail,
                department,
                status
        );

        ApiResponse<List<Employee>> response =
                new ApiResponse<>(
                        true,
                        "Employee fetched successfully",
                        fetchedEmployees);

        return ResponseEntity.ok(response);

    }

    @PatchMapping("/update/{employeeCode}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable String employeeCode,
            @RequestBody Employee employee) {

        ApiResponse<Employee> response =
                new ApiResponse<>(
                        true,
                        "Employee updated successfully",
                        service.updateEmployee(
                                employeeCode,
                                employee));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{employeeCode}")
    public String deleteEmployeeByCode(
            @PathVariable String employeeCode) {

        service.deleteEmployeeByCode(employeeCode);

        return "Employee deleted successfully";
    }
}