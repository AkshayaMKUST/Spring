package com.employee.Employee.controller;

import com.employee.Employee.exception.EmployeeAlreadyExists;
import com.employee.Employee.exception.EmployeeNotFound;
import com.employee.Employee.exception.EmployeeValidationException;
import com.employee.Employee.mapper.EmployeeMapper;
import com.employee.Employee.repository.IEmployeeRepository;
import com.employee.Employee.request.EmployeeRequest;
import com.employee.Employee.response.EmployeeResponse;
import com.employee.Employee.service.IEmployeeService;
import com.ust.pharma.dto.BatchVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import static com.employee.Employee.constants.CommonConstants.FILE_PATH;
import static com.employee.Employee.constants.CommonConstants.POST_BATCH;

@RestController
@Validated
public class EmployeeController {
    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    IEmployeeService employeeService;

    @Autowired
    IEmployeeRepository employeeRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RestTemplate restTemplate;


    @PostMapping("/create")
    public ResponseEntity<EmployeeResponse> createOneEmployee(@RequestHeader("userId") String userId, @RequestBody EmployeeRequest employeeRequest) throws EmployeeAlreadyExists, EmployeeValidationException {
        logger.info("Invoked controller method createOneEmployee with userId: {} and request: {}", userId, employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createOneEmployee(userId, employeeRequest));
    }

    @PostMapping()
    public ResponseEntity<List<EmployeeResponse>> createMultipleEmployee(@RequestHeader("userId") String userId, @RequestBody List<EmployeeRequest> employeeRequests) throws EmployeeAlreadyExists, EmployeeValidationException {
        logger.info("Invoked controller method createMultipleEmployee with userId: {} and request: {}", userId, employeeRequests);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createMultipleEmployee(userId, employeeRequests));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> fetchEmployees() {
        logger.info("Invoked controller method fetchEmployees");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.fetchEmployees());
    }

    @GetMapping("{employeeId}")
    public ResponseEntity<EmployeeResponse> fetchEmployeeById(@PathVariable Integer employeeId) throws EmployeeNotFound {
        logger.info("Invoked controller method fetchEmployeeById with employeeId: {}", employeeId);
        return ResponseEntity.ok(employeeService.fetchEmployeeById(employeeId));
    }

    @PutMapping()
    public ResponseEntity<EmployeeResponse> updateEmployee(String userId, @RequestBody EmployeeRequest employeeRequest) throws EmployeeNotFound {
        logger.info("Invoked controller method updateEmployee with userId: {} and request: {}", userId, employeeRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.updateEmployee(userId, employeeRequest));
    }

    @DeleteMapping("{employeeId}")
    public String deleteEmployee(@PathVariable Integer employeeId) {
        logger.info("Invoked controller method deleteEmployee with employeeId: {}", employeeId);
        employeeService.deleteEmployee(employeeId);
        return "Employee deleted successfully";
    }

    @PostMapping("/upload")
    public ResponseEntity<List<EmployeeResponse>> uploadEmployee(@RequestHeader("userId") String userId) throws IOException, EmployeeValidationException {
        logger.info("Invoked controller method uploadEmployee with userId: {}", userId);
        return ResponseEntity.ok(employeeService.readFromCSV(userId, FILE_PATH));
    }

    @GetMapping("find/{skill}")
    public ResponseEntity<String> findEmployeeBySkill(@PathVariable List<String> skill) {
        logger.info("Invoked controller method findEmployeeBySkill with skill: {}", skill);
        List<EmployeeResponse> employees = employeeService.fetchEmployees();
        List<String> employeesWithSkill = findEmployeeWithSkill(employees, skill);
        if (!employeesWithSkill.isEmpty()) {
            return ResponseEntity.ok("Employees with " + skill + " skill found:" + employeesWithSkill);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employees with " + skill + " skill found!");
        }
    }

    private List<String> findEmployeeWithSkill(List<EmployeeResponse> employees, List<String> requiredSkills) {
        return employees.stream().filter(employeeResponse -> employeeResponse.getEmployeeSkills().containsAll(requiredSkills))
                .map(EmployeeResponse::getEmployeeName)
                .collect(Collectors.toList());
    }

    @GetMapping("total")
    public ResponseEntity<String> findTotalNumberOfEmployees() {
        logger.info("Invoked controller method findTotalNumberOfEmployees");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.findTotalNumberOfEmployees());
    }

    @GetMapping("department")
    public Map<String, List<String>> fetchEmployeesBasedOnDepartment() {
        logger.info("Invoked controller method fetchEmployeesBasedOnDepartment");
        return employeeService.fetchEmployeesBasedOnDepartment();
    }

    @GetMapping("totalSalary")
    public ResponseEntity<String> findTotalSalaryOfEmployees() {
        logger.info("Invoked controller method findTotalSalaryOfEmployees");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.findTotalSalaryOfEmployees());
    }

    @GetMapping("totalSalaryOfDepartment")
    public Map<String, BigDecimal> fetchSalaryBasedOnDepartment() {
        logger.info("Invoked controller method fetchSalaryBasedOnDepartment");
        return employeeService.fetchSalaryBasedOnDepartment();
    }

    @GetMapping("sortAge")
    public ResponseEntity<List<EmployeeResponse>> sortEmployeesBasedOnAge() {
        logger.info("Invoked controller method sortEmployeesBasedOnAge");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.sortEmployeesBasedOnAge());
    }

    @GetMapping("sortExperience")
    public ResponseEntity<List<EmployeeResponse>> sortEmployeesBasedOnExperience() {
        logger.info("Invoked controller method sortEmployeesBasedOnExperience");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.sortEmployeesBasedOnExperience());
    }


    @PostMapping("addBatch")
    public ResponseEntity<String> addBatch(@RequestBody BatchVO batchVO) {
        try {
            logger.info("Invoked controller method addBatch with batchVO: {}", batchVO);
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("admin", "admin");
            HttpEntity<BatchVO> requestEntity = new HttpEntity<>(batchVO,headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(POST_BATCH, requestEntity, String.class);
            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Error occurred while processing addBatch request: {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}

