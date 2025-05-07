package com.employee.Employee.service;
import com.employee.Employee.entity.Employee;
import com.employee.Employee.exception.EmployeeAlreadyExists;
import com.employee.Employee.exception.EmployeeNotFound;
import com.employee.Employee.exception.EmployeeValidationException;
import com.employee.Employee.request.EmployeeRequest;
import com.employee.Employee.response.EmployeeResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IEmployeeService {

    EmployeeResponse createOneEmployee(String userId, EmployeeRequest employeeRequest) throws EmployeeAlreadyExists, EmployeeValidationException;
    List<EmployeeResponse> createMultipleEmployee(String userId, List<EmployeeRequest> employeeRequests) throws EmployeeAlreadyExists, EmployeeValidationException;
    List<EmployeeResponse> fetchEmployees();
    EmployeeResponse fetchEmployeeById(Integer employeeId) throws EmployeeNotFound;
    EmployeeResponse updateEmployee(String userId,EmployeeRequest employeeRequest) throws EmployeeNotFound;
    void deleteEmployee(Integer employeeId);
    List<EmployeeResponse> readFromCSV(String userId, String filePath) throws IOException, EmployeeValidationException;
    String findTotalNumberOfEmployees();
//    List<EmployeeResponse> findEmployeesBySkills(List<String> skill);
    Map<String, List<String>> fetchEmployeesBasedOnDepartment();
    String findTotalSalaryOfEmployees();
    Map<String, BigDecimal> fetchSalaryBasedOnDepartment();
    List<EmployeeResponse> sortEmployeesBasedOnAge();
    List<EmployeeResponse> sortEmployeesBasedOnExperience();


//    Employee createEmployeeWithAddress(Employee employee);
}
