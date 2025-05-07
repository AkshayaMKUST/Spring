package org.employee;

public class Employee {

    int employeeId;
    String employeeName;

    public Employee(int employeeId, String employeeName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return employeeId;
    }

    @Override
    public boolean equals(Object obj) {
        Employee employee = (Employee) obj;
        return employeeId == employee.employeeId;
    }

}
