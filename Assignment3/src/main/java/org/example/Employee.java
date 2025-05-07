package org.example;

import java.util.List;

public class Employee {
    public Integer employeeId;
    public String employeeName;
    public String employeeDepartment;
    public List<String> skills;

    public Employee(Integer employeeId, String employeeName, String employeeDepartment, List<String> skills) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeDepartment = employeeDepartment;
        this.skills = skills;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public List<String> getSkills() {
        return skills;
    }
}
