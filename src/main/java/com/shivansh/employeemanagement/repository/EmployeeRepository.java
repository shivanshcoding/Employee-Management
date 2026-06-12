package com.shivansh.employeemanagement.repository;

import com.shivansh.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository
        extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByEmployeeCode(
            String employeeCode);

    @Query("""
        SELECT e
        FROM Employee e
        WHERE LOWER(e.firstName)
        LIKE LOWER(CONCAT(:prefix,'%'))
       """)
    List<Employee> getEmployeesWhoseNameStartsWith(
            @Param("prefix") String prefix);

    boolean existsByEmployeeCode(
            String employeeCode);

    boolean existsByCompanyEmail(
            String companyEmail);

    boolean existsByPrivateEmail(
            String privateEmail);

    boolean existsByPhoneNumber(String phoneNumber);
}