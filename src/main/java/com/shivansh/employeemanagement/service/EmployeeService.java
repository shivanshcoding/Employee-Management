package com.shivansh.employeemanagement.service;

import com.shivansh.employeemanagement.entity.Department;
import com.shivansh.employeemanagement.entity.Employee;
import com.shivansh.employeemanagement.entity.EmploymentStatus;
import com.shivansh.employeemanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee createEmployee(Employee employee) {

        if (repository.existsByEmployeeCode(
                employee.getEmployeeCode())) {

            throw new RuntimeException(
                    "Employee code already exists");
        }

        if (repository.existsByCompanyEmail(
                employee.getCompanyEmail())) {

            throw new RuntimeException(
                    "Company email already exists");
        }

        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(UUID employeeId) {

        return repository.findById(employeeId)
                .orElse(null);
    }

    public Employee getEmployeeByEmployeeCode(
            String employeeCode) {

        return repository.findByEmployeeCode(
                        employeeCode)
                .orElse(null);
    }

    public List<Employee> getEmployeesByDepartment(
            Department department) {

        return repository.findByDepartment(
                department);
    }

    public List<Employee> getEmployeesByStatus(
            EmploymentStatus status) {

        return repository.findByStatus(
                status);
    }

    public Employee updateEmployee(
            String employeeCode,
            Employee updatedEmployee) {

        Employee employee = getEmployeeByEmployeeCode(employeeCode);

        if (employee == null) {
            throw new RuntimeException(
                    "Employee not found");
        }

        if (updatedEmployee.getFirstName() != null) {
            employee.setFirstName(
                    updatedEmployee.getFirstName());
        }

        if (updatedEmployee.getMiddleName() != null) {
            employee.setMiddleName(
                    updatedEmployee.getMiddleName());
        }

        if (updatedEmployee.getLastName() != null) {
            employee.setLastName(
                    updatedEmployee.getLastName());
        }

        if (updatedEmployee.getPrivateEmail() != null) {
            employee.setPrivateEmail(
                    updatedEmployee.getPrivateEmail());
        }

        if (updatedEmployee.getCompanyEmail() != null) {
            employee.setCompanyEmail(
                    updatedEmployee.getCompanyEmail());
        }

        if (updatedEmployee.getDepartment() != null) {
            employee.setDepartment(
                    updatedEmployee.getDepartment());
        }

        if (updatedEmployee.getStatus() != null) {
            employee.setStatus(
                    updatedEmployee.getStatus());
        }

        if (updatedEmployee.getDesignation() != null) {
            employee.setDesignation(
                    updatedEmployee.getDesignation());
        }

        if (updatedEmployee.getPhoneNumber() != null) {
            employee.setPhoneNumber(
                    updatedEmployee.getPhoneNumber());
        }

        if (updatedEmployee.getSalary() != null) {
            employee.setSalary(
                    updatedEmployee.getSalary());
        }

        return repository.save(employee);
    }

    public void deleteEmployeeById(UUID employeeId) {
        repository.deleteById(employeeId);
    }
    public void deleteEmployeeByCode(String employeeCode) {
        Employee employee = getEmployeeByEmployeeCode(employeeCode);
        if (employee == null) {
            throw new RuntimeException(
                    "Employee not found");
        }
        repository.delete(employee);
    }
}