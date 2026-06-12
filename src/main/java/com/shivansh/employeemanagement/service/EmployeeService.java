package com.shivansh.employeemanagement.service;

import com.shivansh.employeemanagement.dto.CreateEmployeeRequest;
import com.shivansh.employeemanagement.dto.UpdateEmployeeRequest;
import com.shivansh.employeemanagement.entity.Department;
import com.shivansh.employeemanagement.entity.Employee;
import com.shivansh.employeemanagement.entity.EmploymentStatus;
import com.shivansh.employeemanagement.exception.BadRequestException;
import com.shivansh.employeemanagement.exception.DuplicateResourceException;
import com.shivansh.employeemanagement.exception.EmployeeNotFoundException;
import com.shivansh.employeemanagement.exception.EmployeeTerminatedException;
import com.shivansh.employeemanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee createEmployee(CreateEmployeeRequest employee) {

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

        return repository.save(new Employee(employee));
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
            EmploymentStatus status,
            boolean includeTerminated
    ) {

        List<Employee> employees =
                repository.findAll().stream()

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
                .filter(e ->
                        includeTerminated || e.getStatus()
                                != EmploymentStatus.TERMINATED)

                .toList();
        if(employees.isEmpty()){
            throw new EmployeeNotFoundException("No Employee found");
        }
        return employees;
    }

    public Employee updateEmployee(
            String employeeCode,
            UpdateEmployeeRequest updatedEmployee) {
        Employee employee = getEmployeeByCode(employeeCode);

        if (employee.getStatus() == EmploymentStatus.TERMINATED ||
                employee.getStatus() == EmploymentStatus.RESIGNED) {

            throw new EmployeeTerminatedException(
                    "Employee has been terminated, restore the Employee first if you want to update",
                    employee
            );
        }

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
            if (updatedEmployee.getStatus() == EmploymentStatus.TERMINATED) {
                throw new BadRequestException(
                        "Can't update status to TERMINATED from here, do a delete request instead");
            }else{
                employee.setStatus(updatedEmployee.getStatus());
            }
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

    public Employee softDeleteEmployeeByCode(
            String employeeCode) {

        Employee employee = getEmployeeByCode(employeeCode);
        if(employee.getStatus() == EmploymentStatus.TERMINATED){
            throw new BadRequestException("Employee is already terminated");
        }

        employee.setStatus(
                EmploymentStatus.TERMINATED);

        employee.setExitDate(
                LocalDate.now());

        return repository.save(employee);
    }

    public Employee restoreEmployeeByCode(
            String employeeCode) {
        Employee employee = getEmployeeByCode(employeeCode);
        if(employee.getStatus() == EmploymentStatus.TERMINATED){
            employee.setStatus(EmploymentStatus.ACTIVE);
            employee.setExitDate(null);
            return repository.save(employee);
        }
        throw new BadRequestException("Employee is not terminated");
    }

    public void hardDeleteEmployeeByCode(
            String employeeCode) {

        Employee employee =
                getEmployeeByCode(employeeCode);

        repository.delete(employee);
    }

    public List<Employee> getEmployeesByPrefix(
            String prefix) {

        List<Employee> employees =
                repository.getEmployeesWhoseNameStartsWith(prefix)
                        .stream()
                        .filter(e ->
                                e.getStatus()
                                        != EmploymentStatus.TERMINATED)
                        .toList();

        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException(
                    "No employee found");
        }

        return employees;
    }
}