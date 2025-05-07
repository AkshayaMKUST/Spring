package org.employee;
import java.util.*;

public class EmployeeDetails {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Akshaya", "Akshaya@example.com", 90000.0, "HR"));
        employees.add(new Employee(2, "Athulya", "Athulya@example.com", 70000.0, "Development"));
        employees.add(new Employee(1, "Anjana", "Anjana@example.com", 60000.0, "Sales"));
        employees.add(new Employee(4, "Abhirami", "Abhirami@example.com", 70000.0, "Marketing"));
        employees.add(new Employee(5, "Sreelekshmi", "Sree@example.com", 80000.0, "Marketing"));
        employees.add(new Employee(2, "Maria", "Maria@example.com", 90000.0, "Marketing"));


        Set<Integer> employeeIDs = new HashSet<>();


        for (Employee record : employees) {
            employeeIDs.add(record.getEmpId());
        }
        List<Integer> sortedEmployeeIDs = new ArrayList<>(employeeIDs);
        sortedEmployeeIDs.sort(Collections.reverseOrder());
        Map<Integer, Employee> employeeInfo = new HashMap<>();

        for (int empID : sortedEmployeeIDs) {
            for (Employee record : employees) {
                if (record.getEmpId() == empID) {
                    employeeInfo.put(empID, record);
                    break;
                }
            }
        }
        for (int empID : sortedEmployeeIDs) {
            System.out.println("Details of Employee with id: " + empID);
            Employee ob = employeeInfo.get(empID);
            System.out.println("\tEmployee Name: " + ob.getEmpName());
            System.out.println("\tEmployee email: " + ob.getEmpSalary());
            System.out.println("\tEmployee Salary: " + ob.getEmpSalary());
            System.out.println("\tEmployee Department " + ob.getEmpDepartment());
        }
    }
}