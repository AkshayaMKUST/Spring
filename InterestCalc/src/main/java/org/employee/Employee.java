package org.employee;

public class Employee {
    private int empId;
    private String empName;
    private String empEmail;
    private double empSalary;
    private String empDepartment;

    public Employee(int empId, String empName, String empEmail, double empSalary, String empDepartment) {
        this.empId = empId;
        this.empName = empName;
        this.empEmail = empEmail;
        this.empSalary = empSalary;
        this.empDepartment = empDepartment;
    }

    @Override
    public String toString() {
        return empId + ", " + empName + ", " + empEmail + ", " + empSalary + ", " + empDepartment;

    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public double getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(double empSalary) {
        this.empSalary = empSalary;
    }

    public String getEmpDepartment() {
        return empDepartment;
    }

    public void setEmpDepartment(String empDepartment) {
        this.empDepartment = empDepartment;
    }
}