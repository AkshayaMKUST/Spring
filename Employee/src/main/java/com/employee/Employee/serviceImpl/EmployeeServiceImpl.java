package com.employee.Employee.serviceImpl;

import com.employee.Employee.entity.Employee;
import com.employee.Employee.exception.EmployeeAlreadyExists;
import com.employee.Employee.exception.EmployeeNotFound;
import com.employee.Employee.exception.EmployeeValidationException;
import com.employee.Employee.mapper.EmployeeMapper;
import com.employee.Employee.repository.IEmployeeRepository;
import com.employee.Employee.request.EmployeeRequest;
import com.employee.Employee.response.EmployeeResponse;
import com.employee.Employee.service.IEmployeeService;
import com.employee.Employee.validation.EmployeeValidation;
import com.employee.Employee.validation.Error;
import com.employee.Employee.validation.Errors;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class.getName());

    @Autowired
    IEmployeeRepository employeeRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    EmployeeValidation employeeValidation;

    @Override
    public EmployeeResponse createOneEmployee(String userId, EmployeeRequest employeeRequest) throws EmployeeAlreadyExists, EmployeeValidationException {
        logger.info("Creating one employee");
        List<Error> requestErrors = employeeValidation.empValidation(employeeRequest);
        if (!requestErrors.isEmpty()) {
            List<String> errorMessages = requestErrors.stream()
                    .map(Error::getErrorMessage)
                    .toList();
            throw new EmployeeValidationException("Validation failed: " + errorMessages);
        }
        Employee employee = employeeMapper.mapEmployee(userId,employeeRequest);
        addEmployee(employee);
        EmployeeResponse employeeResponse = employeeMapper.mapEmployeeResponse(employee);
        logger.info("Employee created: " + employeeResponse);
        return employeeResponse;
    }

    @Override
    public List<EmployeeResponse> createMultipleEmployee(String userId, List<EmployeeRequest> employeeRequests) throws EmployeeAlreadyExists, EmployeeValidationException {
        logger.info("Creating multiple employees");
        List<EmployeeResponse> createdEmployees = new ArrayList<>();
        for (EmployeeRequest employeeRequest : employeeRequests) {
            List<Error> requestErrors = employeeValidation.empValidation(employeeRequest);
            if (!requestErrors.isEmpty()) {
                List<String> errorMessages = requestErrors.stream()
                        .map(Error::getErrorMessage)
                        .toList();
                throw new EmployeeValidationException("Validation failed: " + errorMessages);
            }
            Employee employee = employeeMapper.mapEmployee(userId,employeeRequest);
            addEmployee(employee);
            createdEmployees.add(employeeMapper.mapEmployeeResponse(employee));
        }
        logger.info("Employees created: " + createdEmployees);
        return createdEmployees;
    }

    public void addEmployee(Employee employee) throws EmployeeAlreadyExists {
        Optional<Employee> existingEmployeeOptional = employeeRepository.findById(employee.getEmployeeId());
        if (existingEmployeeOptional.isPresent()) {
            throw new EmployeeAlreadyExists("Employee with the given id " + employee.getEmployeeId() + " already exists");
        } else {
            employeeRepository.save(employee);
        }
    }

    @Override
    public List<EmployeeResponse> readFromCSV(String userId, String filePath) throws IOException, EmployeeValidationException {
        logger.info("Invoked method readFromCSV");
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(filePath));
        String[] rowValues;
        EmployeeValidation employeeValidation = new EmployeeValidation();
        int rowNumber = 1;
        while(true){
            rowValues = reader.readNext();
            if(rowValues == null){
                break;
            }
            List<Error> errorList = employeeValidation.fileDataValidation(rowValues);
            Employee employee = new Employee();
            if(errorList.isEmpty()) {
                logger.info("Validating CSV row: " + Arrays.toString(rowValues));
                employee.setEmployeeId(Integer.parseInt(rowValues[0]));
                employee.setEmployeeName(rowValues[1]);
                employee.setEmployeeAge(Integer.parseInt(rowValues[2]));
                employee.setEmployeeDepartment(rowValues[3]);
                employee.setEmployeeExperience(Integer.parseInt(rowValues[4]));
                employee.setEmployeeSalary((rowValues[5]));
                employee.setEmployeeSkills(Arrays.asList(rowValues[6].split(",")));
                employee.setCreatedUserId(userId);
                employee.setCreatedTimeStamp(LocalDateTime.now().toString());
                employeeRepository.save(employee);
                employeeResponses.add(employeeMapper.mapEmployeeResponse(employee));
            }
            else{
                logger.warn("Validation failed for CSV row " + rowNumber);
                Errors errors = new Errors();
                List<String> errorMessages = new ArrayList<>();
                errors.setErrorList(errorList);
                for(Error error : errors.getErrorList()){
                    errorMessages.add(error.getErrorMessage());
                }
                throw new EmployeeValidationException(errorMessages.toString()+" Row number : "+rowNumber);
            }
            rowNumber++;
        }
        logger.info("CSV data processed successfully");
        return employeeResponses;
    }

    @Override
    public List<EmployeeResponse> fetchEmployees() {
        logger.info("Fetching all employees");
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponses = employees.stream()
                .map(employeeMapper::mapEmployeeResponse)
                .collect(Collectors.toList());
        logger.info("Fetched employees: " + employeeResponses);
        return employeeResponses;
    }

    @Override
    public EmployeeResponse fetchEmployeeById(Integer employeeId) throws EmployeeNotFound {
        logger.info("Fetching employee by ID: " + employeeId);
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if(employeeOptional.isEmpty()){
            logger.warn("Employee not found with ID: " + employeeId);
            throw new EmployeeNotFound("Employee with the given id does not exist");
        }
        EmployeeResponse employeeResponse = employeeMapper.mapEmployeeResponse(employeeOptional.get());
        logger.info("Fetched employee: " + employeeResponse);
        return employeeResponse;
    }

    @Override
    public EmployeeResponse updateEmployee(String userId, EmployeeRequest employeeRequest) throws EmployeeNotFound {
        logger.info("Updating employee");
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeRequest.getEmployeeId());
        Employee updatedEmployee = employeeOptional.orElseThrow(() -> new EmployeeNotFound("Employee with the given id does not exist"));
        Employee existingEmployee = employeeMapper.mapEmployee(userId, employeeRequest);
        updatedEmployee.setEmployeeName(existingEmployee.getEmployeeName());
        updatedEmployee.setEmployeeAge(existingEmployee.getEmployeeAge());
        updatedEmployee.setEmployeeDepartment(existingEmployee.getEmployeeDepartment());
        updatedEmployee.setEmployeeExperience(existingEmployee.getEmployeeExperience());
        updatedEmployee.setEmployeeSalary(existingEmployee.getEmployeeSalary());
        updatedEmployee.setEmployeeSkills(existingEmployee.getEmployeeSkills());
        EmployeeResponse updatedEmployeeResponse = employeeMapper.mapEmployeeResponse(employeeRepository.save(updatedEmployee));
        logger.info("Updated employee: " + updatedEmployeeResponse);
        return updatedEmployeeResponse;
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        logger.info("Deleting employee with ID: " + employeeId);
        employeeRepository.deleteById(employeeId);
        logger.info("Employee deleted successfully");
    }

    @Override
    public String findTotalNumberOfEmployees() {
        logger.info("Calculating total number of employees");
        List<EmployeeResponse> employeeResponses = fetchEmployees();
        long totalNumberOfEmployees = employeeResponses.size();
        String result = "Total number of employees : " + totalNumberOfEmployees;
        logger.info("Total number of employees: " + totalNumberOfEmployees);
        return result;
    }

    @Override
    public Map<String, List<String>> fetchEmployeesBasedOnDepartment() {
        logger.info("Fetching employees based on department");
        List<EmployeeResponse> employeeResponses = fetchEmployees();
        Map<String, List<String>> departmentEmployeesMap = employeeResponses.stream()
                .collect(Collectors.groupingBy(EmployeeResponse::getEmployeeDepartment,
                        Collectors.mapping(EmployeeResponse::getEmployeeName, Collectors.toList())));
        logger.info("Employees based on department: " + departmentEmployeesMap);
        return departmentEmployeesMap;
    }

    @Override
    public String findTotalSalaryOfEmployees() {
        logger.info("Calculating total salary of employees");
        List<EmployeeResponse> employeeResponses = fetchEmployees();
        BigDecimal totalSalary = employeeResponses.stream()
                .map(EmployeeResponse::getEmployeeSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String result = "Total salary of employees : " + totalSalary;
        logger.info("Total salary of employees: " + totalSalary);
        return result;
    }

    @Override
    public Map<String, BigDecimal> fetchSalaryBasedOnDepartment() {
        logger.info("Fetching salary based on department");
        List<EmployeeResponse> employeeResponses = fetchEmployees();
        Map<String, BigDecimal> salaryByDepartment = employeeResponses.stream()
                .collect(Collectors.groupingBy(EmployeeResponse::getEmployeeDepartment,
                        Collectors.reducing(BigDecimal.ZERO, EmployeeResponse::getEmployeeSalary, BigDecimal::add)));
        logger.info("Salary based on department: " + salaryByDepartment);
        return salaryByDepartment;
    }

    @Override
    public List<EmployeeResponse> sortEmployeesBasedOnAge() {
        logger.info("Sorting employees based on age");
        List<EmployeeResponse> sortedEmployees = fetchEmployees().stream()
                .sorted(Comparator.comparing(EmployeeResponse::getEmployeeAge))
                .collect(Collectors.toList());
        logger.info("Sorted employees based on age: " + sortedEmployees);
        return sortedEmployees;
    }

    @Override
    public List<EmployeeResponse> sortEmployeesBasedOnExperience() {
        logger.info("Sorting employees based on experience");
        List<EmployeeResponse> sortedEmployees = fetchEmployees().stream()
                .sorted(Comparator.comparing(EmployeeResponse::getEmployeeExperience))
                .collect(Collectors.toList());
        logger.info("Sorted employees based on experience: " + sortedEmployees);
        return sortedEmployees;
    }

//    @Override
//    public Employee createEmployeeWithAddress(Employee employee) {
//        Employee savedEmployee = employeeRepository.save(employee);
//        Address address = new Address();
//        if (employee.getAddress() != null) {
//            employee.getAddress().setEmployee(savedEmployee);
//            savedEmployee.setAddress(employee.getAddress());
//            address.setEmployeeId(employee.getEmployeeId());
//        }
//        return savedEmployee;
//
//    }

}
