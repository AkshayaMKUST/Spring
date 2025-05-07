package com.employee.Employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id
    private Integer addressId;
    private String city;
    private String address;

    @OneToOne
    @JoinColumn(name = "employeeId", insertable = false, updatable = false)
    @JsonIgnore
    private Employee employee;
    @Transient
    private Integer employeeId;

    public Integer getEmployeeId() {
        if (employee != null) {
            return employee.getEmployeeId();
        }
        return null;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
}
