package com.employee.Employee.request;

import jakarta.persistence.ElementCollection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EmployeeRequest {
    private Integer employeeId;
    private String employeeName;
    private Integer employeeAge;
    private String employeeDepartment;
    private Integer employeeExperience;
    private BigDecimal employeeSalary;
    private List<String> employeeSkills;
    private String createdUserId;
}
