package com.employee.Employee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    private Integer employeeId;
    private String employeeName;
    private Integer employeeAge;
    private String employeeDepartment;
    private Integer employeeExperience;
    private String employeeSalary;
    @ElementCollection
    private List<String> employeeSkills;
    private String createdUserId;
    private String createdTimeStamp;

//    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
//    private Address address;

//    private String address;
//    private String city;
}
