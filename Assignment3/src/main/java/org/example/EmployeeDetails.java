package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDetails {
    public void employeeFinder() throws EmployeeNotFound{
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Akshaya","HR",List.of("Java", "Python")));
        employees.add(new Employee(2,"Sree","Sales",List.of("Ruby", "C++","Python")));
        Optional<Employee> employeeWithPythonSkill = findEmployeeWithSkill(employees, "Python");
        if (employeeWithPythonSkill.isPresent()) {
            System.out.println("Employee with Python skill found: " + employeeWithPythonSkill.get().getEmployeeName());
        } else {
            throw new EmployeeNotFound("No employees with Python skill found!");
        }
    }

    public Optional<Employee> findEmployeeWithSkill(List<Employee> employees, String skill) {
        return employees.stream().filter(employee -> employee.getSkills().contains(skill)).findFirst();
    }


}
