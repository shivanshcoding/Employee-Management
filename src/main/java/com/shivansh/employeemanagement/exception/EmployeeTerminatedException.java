package com.shivansh.employeemanagement.exception;

import com.shivansh.employeemanagement.entity.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeTerminatedException extends RuntimeException{
    Employee employee;
    public EmployeeTerminatedException(String message, Employee employee) {
        super(message);
        this.employee = employee;
    }
}