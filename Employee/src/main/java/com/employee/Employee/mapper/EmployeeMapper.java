package com.employee.Employee.mapper;

import com.employee.Employee.entity.Employee;
import com.employee.Employee.request.EmployeeRequest;
import com.employee.Employee.response.EmployeeResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class EmployeeMapper {

    public Employee mapEmployee(String userId, EmployeeRequest employeeRequest){
        Employee employee = new Employee();
        employee.setEmployeeId(employeeRequest.getEmployeeId());
        employee.setEmployeeName(employeeRequest.getEmployeeName());
        employee.setEmployeeAge(employeeRequest.getEmployeeAge());
        employee.setEmployeeDepartment(employeeRequest.getEmployeeDepartment());
        employee.setEmployeeExperience(employeeRequest.getEmployeeExperience());
        employee.setEmployeeSalary(employeeRequest.getEmployeeSalary().toString());
        employee.setEmployeeSkills(employeeRequest.getEmployeeSkills());
        employee.setCreatedUserId(userId);
        employee.setCreatedTimeStamp(LocalDateTime.now().toString());
        return employee;
    }

    public EmployeeResponse mapEmployeeResponse(Employee employee){
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(employee.getEmployeeId());
        employeeResponse.setEmployeeName(employee.getEmployeeName());
        employeeResponse.setEmployeeAge(employee.getEmployeeAge());
        employeeResponse.setEmployeeDepartment(employee.getEmployeeDepartment());
        employeeResponse.setEmployeeExperience(employee.getEmployeeExperience());
        employeeResponse.setEmployeeSalary(BigDecimal.valueOf(Double.parseDouble(employee.getEmployeeSalary())));
        employeeResponse.setEmployeeSkills(employee.getEmployeeSkills());
        employeeResponse.setCreatedUserId(employee.getCreatedUserId());
        employeeResponse.setCreatedTimeStamp(employee.getCreatedTimeStamp());
        return employeeResponse;
    }
}
