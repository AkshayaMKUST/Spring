package com.employee.Employee.serviceTest;

import com.employee.Employee.entity.Employee;
import com.employee.Employee.exception.EmployeeAlreadyExists;
import com.employee.Employee.exception.EmployeeNotFound;
import com.employee.Employee.exception.EmployeeValidationException;
import com.employee.Employee.mapper.EmployeeMapper;
import com.employee.Employee.repository.IEmployeeRepository;
import com.employee.Employee.request.EmployeeRequest;
import com.employee.Employee.response.EmployeeResponse;
import com.employee.Employee.serviceImpl.EmployeeServiceImpl;
import com.employee.Employee.validation.EmployeeValidation;
import com.employee.Employee.validation.Error;
import com.employee.Employee.validation.Errors;
import com.opencsv.CSVReader;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    IEmployeeRepository iEmployeeRepository;

    @Mock
    EmployeeMapper employeeMapper;

    @Mock
    EmployeeValidation employeeValidation;

    @Mock
    private CSVReader csvReader;

    @InjectMocks
    EmployeeServiceImpl employeeService;



    @Test
    public void testCreateOneEmployee_ValidationSuccess() throws EmployeeValidationException, EmployeeAlreadyExists {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setEmployeeId(1);
        employeeRequest.setEmployeeName("John Doe");
        employeeRequest.setEmployeeAge(30);
        employeeRequest.setEmployeeDepartment("IT");
        employeeRequest.setEmployeeExperience(5);
        employeeRequest.setEmployeeSalary(new BigDecimal("50000.00"));
        employeeRequest.setEmployeeSkills(Arrays.asList("Java","Python"));
        employeeRequest.setCreatedUserId("user123");
        Employee employee = new Employee();
        EmployeeResponse employeeResponse = new EmployeeResponse();
        List<Error> errorList = new ArrayList<>();
        when(employeeValidation.empValidation(employeeRequest)).thenReturn(errorList);
        when(employeeMapper.mapEmployee(employeeRequest.getCreatedUserId(),employeeRequest)).thenReturn(employee);
        when(iEmployeeRepository.save(Mockito.any())).thenReturn(employee);
        when(employeeMapper.mapEmployeeResponse(Mockito.any())).thenReturn(employeeResponse);
        EmployeeResponse response = employeeService.createOneEmployee(employeeRequest.getCreatedUserId(),employeeRequest);
        assertEquals(response, employeeResponse);
    }

    @Test
    public void testCreateOneEmployee_ValidationFailure(){
        EmployeeRequest employeeRequest= new EmployeeRequest();
        List<Error> errorList = new ArrayList<>();
        Error error = new Error();
        error.setErrorMessage("Name is required");
        errorList.add(error);
        when(employeeValidation.empValidation(employeeRequest)).thenReturn(errorList);
        assertThrows(EmployeeValidationException.class, () -> {
            employeeService.createOneEmployee("user123", employeeRequest);
        });
        verifyNoInteractions(employeeMapper);
        verifyNoInteractions(iEmployeeRepository);
    }

    @Test
    public void testCreateMultipleEmployee_ValidationSuccess() throws EmployeeValidationException, EmployeeAlreadyExists {
        String userId = "user123";
        List<EmployeeRequest> employeeRequests = new ArrayList<>();
        EmployeeRequest employeeRequest1 = new EmployeeRequest();
        employeeRequest1.setEmployeeId(1);
        employeeRequest1.setEmployeeName("John Doe");
        employeeRequest1.setEmployeeAge(30);
        employeeRequest1.setEmployeeDepartment("IT");
        employeeRequest1.setEmployeeExperience(5);
        employeeRequest1.setEmployeeSalary(new BigDecimal("50000.00"));
        employeeRequest1.setEmployeeSkills(Arrays.asList("Java", "Python"));
        employeeRequests.add(employeeRequest1);

        EmployeeRequest employeeRequest2 = new EmployeeRequest();
        employeeRequest2.setEmployeeId(2);
        employeeRequest2.setEmployeeName("Jane Smith");
        employeeRequest2.setEmployeeAge(35);
        employeeRequest2.setEmployeeDepartment("HR");
        employeeRequest2.setEmployeeExperience(8);
        employeeRequest2.setEmployeeSalary(new BigDecimal("60000.00"));
        employeeRequest2.setEmployeeSkills(Arrays.asList("Communication", "Leadership"));
        employeeRequests.add(employeeRequest2);

        when(employeeValidation.empValidation(Mockito.any())).thenReturn(Collections.emptyList());

        Employee employee = new Employee();
        EmployeeResponse employeeResponse = new EmployeeResponse();
        when(employeeMapper.mapEmployee(Mockito.anyString(), Mockito.any(EmployeeRequest.class))).thenReturn(employee);
        when(employeeMapper.mapEmployeeResponse(Mockito.any(Employee.class))).thenReturn(employeeResponse);

        List<EmployeeResponse> responses = employeeService.createMultipleEmployee(userId, employeeRequests);

        assertEquals(employeeRequests.size(), responses.size());
        verify(employeeValidation, times(employeeRequests.size())).empValidation(Mockito.any(EmployeeRequest.class));
        verify(employeeMapper, times(employeeRequests.size())).mapEmployee(Mockito.anyString(), Mockito.any(EmployeeRequest.class));
        verify(employeeMapper, times(employeeRequests.size())).mapEmployeeResponse(Mockito.any(Employee.class));
        verify(iEmployeeRepository, times(employeeRequests.size())).save(Mockito.any(Employee.class));
    }

    @Test
    public void testCreateMultipleEmployee_ValidationFailure() {
        String userId = "user123";
        List<EmployeeRequest> employeeRequests = new ArrayList<>();

        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setEmployeeId(1);
        employeeRequest.setEmployeeAge(30);
        employeeRequest.setEmployeeDepartment("IT");
        employeeRequest.setEmployeeExperience(5);
        employeeRequest.setEmployeeSalary(new BigDecimal("50000.00"));
        employeeRequest.setEmployeeSkills(Arrays.asList("Java", "Python"));
        employeeRequests.add(employeeRequest);

        List<Error> errorList = new ArrayList<>();
        Error error = new Error();
        error.setErrorMessage("Name is required");
        errorList.add(error);
        when(employeeValidation.empValidation(Mockito.any())).thenReturn(errorList);

        assertThrows(EmployeeValidationException.class, () -> {
            employeeService.createMultipleEmployee(userId, employeeRequests);
        });

        verifyNoInteractions(employeeMapper);
        verifyNoInteractions(iEmployeeRepository);
    }

    @Test
    public void testAddEmployee_Success() throws EmployeeAlreadyExists {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setEmployeeAge(30);
        employee.setEmployeeDepartment("IT");
        employee.setEmployeeExperience(5);
        employee.setEmployeeSalary("50000.00");
        employee.setEmployeeSkills(Arrays.asList("Java", "Python"));
        when(iEmployeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        employeeService.addEmployee(employee);
        verify(iEmployeeRepository,times(1)).findById(Mockito.anyInt());
        verify(iEmployeeRepository,times(1)).save(Mockito.any());
    }

    @Test
    public void testAddEmployee_Failure() throws EmployeeAlreadyExists {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setEmployeeAge(30);
        employee.setEmployeeDepartment("IT");
        employee.setEmployeeExperience(5);
        employee.setEmployeeSalary("50000.00");
        employee.setEmployeeSkills(Arrays.asList("Java", "Python"));
        when(iEmployeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(employee));
        assertThrows(EmployeeAlreadyExists.class, () -> {
            employeeService.addEmployee(employee);
        });
        verify(iEmployeeRepository,times(1)).findById(Mockito.anyInt());
        verify(iEmployeeRepository,never()).save(Mockito.any());
    }

    @Test
    public void testFetchEmployees(){
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setEmployeeId(1);
        employee1.setEmployeeName("John Doe");
        employee1.setEmployeeAge(30);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeExperience(5);
        employee1.setEmployeeSalary("50000.00");
        employee1.setEmployeeSkills(Arrays.asList("Java", "Python"));
        employees.add(employee1);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2);
        employee2.setEmployeeName("Jane Smith");
        employee2.setEmployeeAge(35);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeExperience(8);
        employee2.setEmployeeSalary("60000.00");
        employee2.setEmployeeSkills(Arrays.asList("Communication", "Leadership"));
        employees.add(employee2);
        when(iEmployeeRepository.findAll()).thenReturn(employees);
        List<EmployeeResponse> employeeResponses = employeeService.fetchEmployees();
        verify(iEmployeeRepository,times(1)).findAll();
        assertEquals(employeeResponses,employeeService.fetchEmployees());
    }
    @Test
    public void testFetchEmployeeById_Exists() throws EmployeeNotFound {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setEmployeeAge(30);
        employee.setEmployeeDepartment("IT");
        employee.setEmployeeExperience(5);
        employee.setEmployeeSalary("50000.00");
        employee.setEmployeeSkills(Arrays.asList("Java", "Python"));
        when(iEmployeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(employee));
        EmployeeResponse employeeResponse = employeeService.fetchEmployeeById(Mockito.anyInt());
        verify(iEmployeeRepository,times(1)).findById(Mockito.anyInt());
        assertEquals(employeeResponse,employeeService.fetchEmployeeById(Mockito.anyInt()));
    }
    @Test
    public void testFetchEmployeeById_NotExist() throws EmployeeNotFound {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setEmployeeAge(30);
        employee.setEmployeeDepartment("IT");
        employee.setEmployeeExperience(5);
        employee.setEmployeeSalary("50000.00");
        employee.setEmployeeSkills(Arrays.asList("Java", "Python"));
        when(iEmployeeRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFound.class, () -> {
            employeeService.fetchEmployeeById(Mockito.anyInt());
        });
        verify(iEmployeeRepository,times(1)).findById(Mockito.anyInt());
    }



    @Test
    public void testUpdateEmployee() throws EmployeeNotFound {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setEmployeeId(1);
        employeeRequest.setEmployeeName("James");
        employeeRequest.setEmployeeAge(30);
        employeeRequest.setEmployeeDepartment("IT");
        employeeRequest.setEmployeeExperience(5);
        employeeRequest.setEmployeeSalary(new BigDecimal("50000.00"));
        employeeRequest.setEmployeeSkills(Arrays.asList("Java", "Python"));
        Employee existingEmployee = new Employee();
        existingEmployee.setEmployeeId(1);
        existingEmployee.setEmployeeName("Joe");
        existingEmployee.setEmployeeAge(30);
        existingEmployee.setEmployeeDepartment("IT");
        existingEmployee.setEmployeeExperience(5);
        existingEmployee.setEmployeeSalary("50000.00");
        existingEmployee.setEmployeeSkills(Arrays.asList("Java", "Python"));
        Optional<Employee> employeeOptional = Optional.of(existingEmployee);
        when(iEmployeeRepository.findById(Mockito.anyInt())).thenReturn(employeeOptional);
        when(iEmployeeRepository.save(Mockito.any(Employee.class))).thenReturn(existingEmployee);
        when(employeeMapper.mapEmployee(Mockito.anyString(), Mockito.any(EmployeeRequest.class)))
                .thenReturn(existingEmployee);
        EmployeeResponse response = employeeService.updateEmployee("userId", employeeRequest);
        assertEquals("Joe", existingEmployee.getEmployeeName());
        verify(iEmployeeRepository, times(1)).findById(Mockito.anyInt());
        verify(iEmployeeRepository, times(1)).save(Mockito.any(Employee.class));
    }
    @Test
    public void testFindTotalNumberOfEmployees(){
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        employeeResponses.add(mockEmployeeResponse());
        employeeResponses.add(mockEmployeeResponse());
        when(employeeService.fetchEmployees()).thenReturn(employeeResponses);
        String result = employeeService.findTotalNumberOfEmployees();

        assertEquals("Total number of employees : 3", result);
    }

    @Test
    public void testDeleteEmployee(){
        employeeService.deleteEmployee(1);
        verify(iEmployeeRepository,times(1)).deleteById(Mockito.anyInt());

    }

    @Test
    public void testUpdateEmployees() throws EmployeeNotFound {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setEmployeeId(1);
        Employee existingEmployee = new Employee();
        existingEmployee.setEmployeeId(1);
        existingEmployee.setEmployeeName("James");
        existingEmployee.setEmployeeAge(20);
        existingEmployee.setEmployeeDepartment("Sales");
        existingEmployee.setEmployeeSalary("40000");
        existingEmployee.setEmployeeSkills(Arrays.asList("Java", "Python"));
        Optional<Employee> optionalEmployee = Optional.of(existingEmployee);
        when(iEmployeeRepository.findById(Mockito.anyInt())).thenReturn(optionalEmployee);
        when(employeeMapper.mapEmployee(Mockito.anyString(),Mockito.any())).thenReturn(existingEmployee);
        employeeService.updateEmployee("user123",employeeRequest);
    }

    @Test
    public void readFromCSV_ValidData_ShouldReturnEmployeeResponses() throws IOException, EmployeeValidationException {
        String userId = "user123";
        String filePath = "C:/Users/245239/Desktop/Employ Upload.csv";
        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{"1", "John Doe", "30", "IT", "5", "5000", "Java,C++"});
        csvData.add(new String[]{"2", "Jane Smith", "28", "HR", "3", "4000", "Communication,Team Management"});
        List<EmployeeResponse> result = employeeService.readFromCSV(userId, filePath);
    }

    @Test
    public void testReadFromCSV_ValidationFailure() {
        String userId = "user123";
        String filePath = "C:/Users/245239/Desktop/Employ Upload1.csv";
        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{"1", "John Doe", "30", "IT", "5", "5000", "Java,C++"});
        csvData.add(new String[]{"2", "40","HR", "3", "4000", "Communication,Team Management"});
        List<Error> errorList = new ArrayList<>();
        Error error = new Error();
        error.setErrorMessage("Name is required");
        errorList.add(error);
        assertThrows(EmployeeValidationException.class,()->{
            employeeService.readFromCSV(userId,filePath);
        });
    }


    private EmployeeResponse mockEmployeeResponse(){
        EmployeeResponse employeeResponse1 = new EmployeeResponse();
        employeeResponse1.setEmployeeId(1);
        employeeResponse1.setEmployeeName("Ahaan");
        employeeResponse1.setEmployeeAge(30);
        employeeResponse1.setEmployeeDepartment("IT");
        employeeResponse1.setEmployeeExperience(5);
        employeeResponse1.setEmployeeSalary(new BigDecimal("50000.00"));
        employeeResponse1.setEmployeeSkills(Arrays.asList("Java", "Python"));
        return employeeResponse1;
    }
}


