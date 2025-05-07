package com.employee.Employee.response;

import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class EmployeeResponse {
    private Integer employeeId;
    private String employeeName;
    private Integer employeeAge;
    private String employeeDepartment;
    private Integer employeeExperience;
    private BigDecimal employeeSalary;
    @ElementCollection
    private List<String> employeeSkills;
    private String createdUserId;
    private String createdTimeStamp;
}
