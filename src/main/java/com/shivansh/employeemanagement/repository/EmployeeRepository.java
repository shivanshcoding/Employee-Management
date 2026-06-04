package com.shivansh.employeemanagement.repository;

import com.shivansh.employeemanagement.entity.Department;
import com.shivansh.employeemanagement.entity.Employee;
import com.shivansh.employeemanagement.entity.EmploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository
        extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByEmployeeCode(
            String employeeCode);

    Optional<Employee> findByCompanyEmail(
            String companyEmail);

    Optional<Employee> findByPrivateEmail(
            String privateEmail);

    List<Employee> findByFirstName(
            String firstName);

    List<Employee> findByLastName(
            String lastName);

    List<Employee> findByDepartment(
            Department department);

    List<Employee> findByStatus(
            EmploymentStatus status);

    boolean existsByEmployeeCode(
            String employeeCode);

    boolean existsByCompanyEmail(
            String companyEmail);

    boolean existsByPrivateEmail(
            String privateEmail);

    boolean existsByPhoneNumber(String phoneNumber);
}