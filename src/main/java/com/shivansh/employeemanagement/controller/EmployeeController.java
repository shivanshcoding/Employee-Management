package com.shivansh.employeemanagement.controller;

import com.shivansh.employeemanagement.dto.ApiResponse;
import com.shivansh.employeemanagement.dto.CreateEmployeeRequest;
import com.shivansh.employeemanagement.dto.UpdateEmployeeRequest;
import com.shivansh.employeemanagement.entity.Department;
import com.shivansh.employeemanagement.entity.Employee;
import com.shivansh.employeemanagement.entity.EmploymentStatus;
import com.shivansh.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Employee>> createEmployee(
            @Valid @RequestBody CreateEmployeeRequest employeeData) {

        Employee newEmployeeCreated = service.createEmployee(employeeData);
        ApiResponse<Employee> response =
                new ApiResponse<>(
                        true,
                        "Employee created successfully",
                        newEmployeeCreated);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
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
            EmploymentStatus status,

            @RequestParam(required = false, defaultValue = "false")
            boolean includeTerminated
    ){

        List<Employee> fetchedEmployees =  service.fetchEmployees(
                employeeCode,
                companyEmail,
                privateEmail,
                department,
                status,
                includeTerminated
        );

        ApiResponse<List<Employee>> response =
                new ApiResponse<>(
                        true,
                        "Employee fetched successfully",
                        fetchedEmployees);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/fetch/prefix")
    public ResponseEntity<ApiResponse<List<Employee>>>
    getEmployeesByPrefix(
            @RequestParam()
            String prefix
    ) {
        List<Employee> employees= service.getEmployeesByPrefix(prefix);

        ApiResponse<List<Employee>> response =
                new ApiResponse<>(
                        true,
                        "Employees Found Successfully",
                        employees);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update/{employeeCode}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable String employeeCode,
            @RequestBody UpdateEmployeeRequest employee) {

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
    public ResponseEntity<ApiResponse<Employee>>
    softDeleteEmployeeByCode(
            @PathVariable String employeeCode) {

        Employee employee =
                service.softDeleteEmployeeByCode(
                        employeeCode);

        ApiResponse<Employee> response =
                new ApiResponse<>(
                        true,
                        "Employee terminated successfully",
                        employee);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/restore/{employeeCode}")
    public ResponseEntity<ApiResponse<Employee>>
    restoreEmployeeByCode(
            @PathVariable String employeeCode) {

        Employee employee =
                service.restoreEmployeeByCode(
                        employeeCode);

        ApiResponse<Employee> response =
                new ApiResponse<>(
                        true,
                        "Employee restored successfully",
                        employee);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/hard-delete/{employeeCode}")
    public ResponseEntity<ApiResponse<Void>>
    hardDeleteEmployeeByCode(
            @PathVariable String employeeCode) {


        service.hardDeleteEmployeeByCode(
                        employeeCode);

        ApiResponse<Void> response =
                new ApiResponse<>(
                        true,
                        "Employee deleted successfully",
                        null);

        return ResponseEntity.ok(response);
    }

}