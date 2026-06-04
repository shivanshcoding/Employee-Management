package com.shivansh.employeemanagement.controller;

import com.shivansh.employeemanagement.entity.Department;
import com.shivansh.employeemanagement.entity.Employee;
import com.shivansh.employeemanagement.entity.EmploymentStatus;
import com.shivansh.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public Employee createEmployee(
            @Valid @RequestBody Employee employee) {

        return service.createEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {

        return service.getAllEmployees();
    }

    @GetMapping("/id/{employeeId}")
    public Employee getEmployeeById(
            @PathVariable UUID employeeId) {

        return service.getEmployeeById(employeeId);
    }

    @GetMapping("/code/{employeeCode}")
    public Employee getEmployeeByCode(
            @PathVariable String employeeCode) {

        return service.getEmployeeByCode(employeeCode);
    }

    @GetMapping("/email/{companyEmail}")
    public Employee getEmployeeByCompanyEmail(
            @PathVariable String companyEmail) {

        return service.getEmployeeByCompanyEmail(
                companyEmail);
    }

    @GetMapping("/department/{department}")
    public List<Employee> getEmployeesByDepartment(
            @PathVariable Department department) {

        return service.getEmployeesByDepartment(
                department);
    }

    @GetMapping("/status/{status}")
    public List<Employee> getEmployeesByStatus(
            @PathVariable EmploymentStatus status) {

        return service.getEmployeesByStatus(
                status);
    }

    @PatchMapping("/{employeeCode}")
    public Employee updateEmployee(
            @PathVariable String employeeCode,
            @RequestBody Employee employee) {

        return service.updateEmployee(
                employeeCode,
                employee);
    }

    @DeleteMapping("/id/{employeeId}")
    public String deleteEmployeeById(
            @PathVariable UUID employeeId) {

        service.deleteEmployeeById(employeeId);

        return "Employee deleted successfully";
    }

    @DeleteMapping("/code/{employeeCode}")
    public String deleteEmployeeByCode(
            @PathVariable String employeeCode) {

        service.deleteEmployeeByCode(employeeCode);

        return "Employee deleted successfully";
    }
}