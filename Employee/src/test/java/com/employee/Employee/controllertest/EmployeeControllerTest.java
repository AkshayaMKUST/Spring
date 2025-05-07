package com.employee.Employee.controllertest;

import com.employee.Employee.controller.EmployeeController;
import com.employee.Employee.exception.EmployeeAlreadyExists;
import com.employee.Employee.exception.EmployeeNotFound;
import com.employee.Employee.exception.EmployeeValidationException;
import com.employee.Employee.request.EmployeeRequest;
import com.employee.Employee.response.EmployeeResponse;
import com.employee.Employee.service.IEmployeeService;
import com.ust.pharma.dto.BatchVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    IEmployeeService employeeService;

    @Mock
    RestTemplate restTemplate;

    private String userId = "user123";
    private String filePath = "C:/Users/245239/Desktop/Employ Upload.csv";


    @Test
    public void testCreateOneEmployee_Success() throws EmployeeAlreadyExists, EmployeeValidationException {
        EmployeeRequest employeeRequest = createEmployeeRequest();
        EmployeeResponse expectedResponse = createEmployeeResponse();
        when(employeeService.createOneEmployee(eq(userId), any(EmployeeRequest.class))).thenReturn(expectedResponse);
        ResponseEntity<EmployeeResponse> responseEntity = employeeController.createOneEmployee(userId, employeeRequest);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(employeeService, times(1)).createOneEmployee(eq(userId), eq(employeeRequest));
    }

    @Test
    public void testCreateMultipleEmployee_Success() throws EmployeeAlreadyExists, EmployeeValidationException {
        List<EmployeeRequest> employeeRequests = Arrays.asList(createEmployeeRequest(), createEmployeeRequest());
        List<EmployeeResponse> expectedResponses = Arrays.asList(createEmployeeResponse(), createEmployeeResponse());
        when(employeeService.createMultipleEmployee(eq(userId), anyList())).thenReturn(expectedResponses);
        ResponseEntity<List<EmployeeResponse>> responseEntity = employeeController.createMultipleEmployee(userId, employeeRequests);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponses, responseEntity.getBody());
        verify(employeeService, times(1)).createMultipleEmployee(eq(userId), eq(employeeRequests));
    }

    @Test
    public void testFetchEmployees(){
        List<EmployeeResponse> expectedResponses = Arrays.asList(createEmployeeResponse(),createEmployeeResponse());
        when(employeeService.fetchEmployees()).thenReturn(expectedResponses);
        ResponseEntity<List<EmployeeResponse>> responseEntity = employeeController.fetchEmployees();
        assertEquals(HttpStatus.ACCEPTED,responseEntity.getStatusCode());
        assertEquals(expectedResponses,responseEntity.getBody());
        verify(employeeService,times(1)).fetchEmployees();
    }

    @Test
    public void testFetchEmployeeById() throws EmployeeNotFound {
        EmployeeResponse expectedResponse = createEmployeeResponse();
        when(employeeService.fetchEmployeeById(eq(createEmployeeRequest().getEmployeeId()))).thenReturn(expectedResponse);
        ResponseEntity<EmployeeResponse> responseEntity = employeeController.fetchEmployeeById(createEmployeeRequest().getEmployeeId());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(expectedResponse,responseEntity.getBody());
        verify(employeeService,times(1)).fetchEmployeeById(createEmployeeRequest().getEmployeeId());
    }

    private EmployeeRequest createEmployeeRequest() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setEmployeeId(1);
        employeeRequest.setEmployeeName("John Doe");
        employeeRequest.setEmployeeAge(30);
        employeeRequest.setEmployeeDepartment("IT");
        employeeRequest.setEmployeeExperience(5);
        employeeRequest.setEmployeeSalary(BigDecimal.valueOf(50000));
        employeeRequest.setEmployeeSkills(Arrays.asList("Java", "Spring"));
        employeeRequest.setCreatedUserId(userId);
        return employeeRequest;
    }

    private EmployeeResponse createEmployeeResponse() {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmployeeId(1);
        employeeResponse.setEmployeeName("John Doe");
        employeeResponse.setEmployeeAge(30);
        employeeResponse.setEmployeeDepartment("IT");
        employeeResponse.setEmployeeExperience(5);
        employeeResponse.setEmployeeSalary(BigDecimal.valueOf(50000));
        employeeResponse.setEmployeeSkills(Arrays.asList("Java", "Spring"));
        employeeResponse.setCreatedUserId(userId);
        return employeeResponse;
    }

    @Test
    public void testUpdateEmployee() throws EmployeeNotFound {
        EmployeeResponse expectedResponse = createEmployeeResponse();
        EmployeeRequest employeeRequest = createEmployeeRequest();
        when(employeeService.updateEmployee(userId,employeeRequest)).thenReturn(expectedResponse);
        ResponseEntity<EmployeeResponse> responseEntity = employeeController.updateEmployee(userId,employeeRequest);
        assertEquals(HttpStatus.ACCEPTED,responseEntity.getStatusCode());
        assertEquals(expectedResponse,responseEntity.getBody());
        verify(employeeService,times(1)).updateEmployee(userId,employeeRequest);
    }

    @Test
    public void testDeleteEmployee(){
        doNothing().when(employeeService).deleteEmployee(eq(createEmployeeRequest().getEmployeeId()));
        String response = employeeController.deleteEmployee(createEmployeeRequest().getEmployeeId());
        verify(employeeService,times(1)).deleteEmployee(createEmployeeRequest().getEmployeeId());
    }

    @Test
    public void testUploadEmployee() throws EmployeeValidationException, IOException {
        List<EmployeeResponse> expectedResponses = Arrays.asList(createEmployeeResponse(),createEmployeeResponse());
        when(employeeService.readFromCSV(userId,filePath)).thenReturn(expectedResponses);
        ResponseEntity<List<EmployeeResponse>> responseEntity = employeeController.uploadEmployee(userId);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(expectedResponses,responseEntity.getBody());
        verify(employeeService,times(1)).readFromCSV(userId,filePath);
    }

    @Test
    public void testFindEmployeeBySkill_WithSkillFound() {
        List<String> skill = Arrays.asList("Java", "Spring");
        List<EmployeeResponse> employees = Arrays.asList(createEmployeeResponse());
        when(employeeService.fetchEmployees()).thenReturn(employees);
        ResponseEntity<String> responseEntity = employeeController.findEmployeeBySkill(skill);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Employees with " + skill + " skill found:[" + createEmployeeResponse().getEmployeeName() + "]", responseEntity.getBody());
    }

    @Test
    public void testFindEmployeeBySkill_WithSkillNotFound() {
        List<String> skill = Arrays.asList("Python");
        when(employeeService.fetchEmployees()).thenReturn(Arrays.asList());
        ResponseEntity<String> responseEntity = employeeController.findEmployeeBySkill(skill);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("No employees with " + skill + " skill found!", responseEntity.getBody());
    }

    @Test
    public void testFindTotalNumberOfEmployees() {
        String totalEmployees = "10";
        when(employeeService.findTotalNumberOfEmployees()).thenReturn(totalEmployees);
        ResponseEntity<String> responseEntity = employeeController.findTotalNumberOfEmployees();
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(totalEmployees, responseEntity.getBody());
    }

    @Test
    public void testFetchEmployeesBasedOnDepartment() {
        Map<String, List<String>> departments = createDepartmentMap();
        when(employeeService.fetchEmployeesBasedOnDepartment()).thenReturn(departments);
        Map<String, List<String>> result = employeeController.fetchEmployeesBasedOnDepartment();
        assertEquals(departments, result);
    }

    @Test
    public void testFindTotalSalaryOfEmployees() {
        String totalSalary = "500000";
        when(employeeService.findTotalSalaryOfEmployees()).thenReturn(totalSalary);
        ResponseEntity<String> responseEntity = employeeController.findTotalSalaryOfEmployees();
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(totalSalary, responseEntity.getBody());
    }

    @Test
    public void testFetchSalaryBasedOnDepartment() {
        Map<String, BigDecimal> salaryMap = createSalaryMap();
        when(employeeService.fetchSalaryBasedOnDepartment()).thenReturn(salaryMap);
        Map<String, BigDecimal> result = employeeController.fetchSalaryBasedOnDepartment();
        assertEquals(salaryMap, result);
    }

    @Test
    public void testSortEmployeesBasedOnAge() {
        List<EmployeeResponse> employees = Arrays.asList(createEmployeeResponse());
        when(employeeService.sortEmployeesBasedOnAge()).thenReturn(employees);
        ResponseEntity<List<EmployeeResponse>> responseEntity = employeeController.sortEmployeesBasedOnAge();
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(employees, responseEntity.getBody());
    }

    @Test
    public void testSortEmployeesBasedOnExperience() {
        List<EmployeeResponse> employees = Arrays.asList(createEmployeeResponse());
        when(employeeService.sortEmployeesBasedOnExperience()).thenReturn(employees);
        ResponseEntity<List<EmployeeResponse>> responseEntity = employeeController.sortEmployeesBasedOnExperience();
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(employees, responseEntity.getBody());
    }

    @Test
    public void testAddBatch(){
        BatchVO batchVO = new BatchVO("BTC-1200","MC_301",new BigDecimal(500),new BigDecimal(1000),"C","yes");
        HttpEntity<BatchVO> request = new HttpEntity<>(batchVO);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Batch Added Successfully",HttpStatus.OK);
        when(restTemplate.postForEntity(Mockito.anyString(),any(HttpEntity.class),eq(String.class))).thenReturn(responseEntity);
        ResponseEntity<String> result = employeeController.addBatch(batchVO);
        assertEquals(result,employeeController.addBatch(batchVO));
    }

    @Test
    public void testAddBatch_Failure() {
        BatchVO batchVO = new BatchVO("BTC-1205", "MC_301", new BigDecimal(500), new BigDecimal(1000), "C", "yes");
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred");
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenThrow(exception);
        ResponseEntity<String> result = employeeController.addBatch(batchVO);
        assertEquals(result.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, List<String>> createDepartmentMap() {
        Map<String, List<String>> departmentMap = new HashMap<>();
        departmentMap.put("IT", List.of("John", "James"));
        departmentMap.put("HR", List.of("Sam", "Alex"));
        return departmentMap;
    }

    private Map<String, BigDecimal> createSalaryMap() {
        Map<String, BigDecimal> salaryMap = new HashMap<>();
        salaryMap.put("IT", BigDecimal.valueOf(10000));
        salaryMap.put("HR", BigDecimal.valueOf(8000));
        return salaryMap;
    }



}


//    @Test
//    public void testCreateEmployeeWithAddress() {
//        Employee employee = new Employee();
//        Employee createdEmployee = createEmployeeResponse().toEmployee(); // Assuming a method to convert EmployeeResponse to Employee
//        when(employeeService.createEmployeeWithAddress(employee)).thenReturn(createdEmployee);
//        ResponseEntity<Employee> responseEntity = employeeController.createEmployeeWithAddress(employee);
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        assertEquals(createdEmployee, responseEntity.getBody());
//    }