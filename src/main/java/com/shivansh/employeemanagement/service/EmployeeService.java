package com.shivansh.employeemanagement.service;

import com.shivansh.employeemanagement.entity.Department;
import com.shivansh.employeemanagement.entity.Employee;
import com.shivansh.employeemanagement.entity.EmploymentStatus;
import com.shivansh.employeemanagement.exception.DuplicateResourceException;
import com.shivansh.employeemanagement.exception.EmployeeNotFoundException;
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

        if (repository.existsByEmployeeCode(employee.getEmployeeCode())) {
            throw new DuplicateResourceException("Employee already exists with this code");
        }

        if (repository.existsByCompanyEmail(employee.getCompanyEmail())) {
            throw new DuplicateResourceException("Employee already exists with this company email");
        }

        if (repository.existsByPrivateEmail(employee.getPrivateEmail())) {
            throw new DuplicateResourceException("Employee already exists with this private email");
        }

        if (repository.existsByPhoneNumber(employee.getPhoneNumber())){
            throw new DuplicateResourceException("Employee already exists with this phone number");
        }

        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeByCode(
            String employeeCode) {

        Employee employee =
                repository.findByEmployeeCode(employeeCode)
                        .orElse(null);

        if(employee == null){
            throw new EmployeeNotFoundException("Employee not found with this Code");
        }

        return employee;
    }

    public List<Employee> fetchEmployees(
            String employeeCode,
            String companyEmail,
            String privateEmail,
            Department department,
            EmploymentStatus status
    ) {

        List<Employee> employees =
                repository.findAll();

        return employees.stream()

                .filter(e ->
                        employeeCode == null ||
                                e.getEmployeeCode()
                                        .equals(employeeCode))

                .filter(e ->
                        companyEmail == null ||
                                e.getCompanyEmail()
                                        .equals(companyEmail))

                .filter(e ->
                        privateEmail == null ||
                                e.getPrivateEmail()
                                        .equals(privateEmail))

                .filter(e ->
                        department == null ||
                                e.getDepartment()
                                        .equals(department))

                .filter(e ->
                        status == null ||
                                e.getStatus()
                                        .equals(status))

                .toList();
    }

    public Employee updateEmployee(
            String employeeCode,
            Employee updatedEmployee) {

        Employee employee = getEmployeeByCode(employeeCode);

        if (updatedEmployee.getFirstName() != null) {
            employee.setFirstName(updatedEmployee.getFirstName());
        }

        if (updatedEmployee.getMiddleName() != null) {
            employee.setMiddleName(updatedEmployee.getMiddleName());
        }

        if (updatedEmployee.getLastName() != null) {
            employee.setLastName(updatedEmployee.getLastName());
        }

        if (updatedEmployee.getPrivateEmail() != null &&
                !updatedEmployee.getPrivateEmail().equals(employee.getPrivateEmail())) {

            if (repository.existsByPrivateEmail(updatedEmployee.getPrivateEmail())) {
                throw new DuplicateResourceException("Employee already exists with this private email");
            }

            employee.setPrivateEmail(updatedEmployee.getPrivateEmail());
        }

        if (updatedEmployee.getCompanyEmail() != null &&
                !updatedEmployee.getCompanyEmail().equals(employee.getCompanyEmail())) {

            if (repository.existsByCompanyEmail(updatedEmployee.getCompanyEmail())) {
                throw new DuplicateResourceException("Employee already exists with this company email");
            }

            employee.setCompanyEmail(updatedEmployee.getCompanyEmail());
        }

        if (updatedEmployee.getDepartment() != null) {
            employee.setDepartment(updatedEmployee.getDepartment());
        }

        if (updatedEmployee.getStatus() != null) {
            employee.setStatus(updatedEmployee.getStatus());
        }

        if (updatedEmployee.getDesignation() != null) {
            employee.setDesignation(updatedEmployee.getDesignation());
        }

        if (updatedEmployee.getPhoneNumber() != null &&
                !updatedEmployee.getPhoneNumber().equals(employee.getPhoneNumber())) {

            if (repository.existsByPhoneNumber(updatedEmployee.getPhoneNumber())) {
                throw new DuplicateResourceException("Employee already exists with this phone number");
            }

            employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        }

        if (updatedEmployee.getSalary() != null) {
            employee.setSalary(updatedEmployee.getSalary());
        }

        return repository.save(employee);
    }

    public void deleteEmployeeById(UUID employeeId) {
        repository.deleteById(employeeId);
    }
    public void deleteEmployeeByCode(String employeeCode) { repository.delete(getEmployeeByCode(employeeCode)); }
}