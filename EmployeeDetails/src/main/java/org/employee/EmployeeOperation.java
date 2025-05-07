package org.employee;
import java.util.*;
public class EmployeeOperation {
    public void employeeAddressMap() {
        Map<Employee, Address> employeeAddressMap = new HashMap<>();
        employeeAddressMap.put(new Employee(1, "Akshaya"), new Address(101, "MS Street", "Los angeles", 695013));
        employeeAddressMap.put(new Employee(2, "Abhi"), new Address(103, "Neo Street", "Chicago", 632421));
        employeeAddressMap.put(new Employee(3, "Sree"), new Address(203, "Elm St", "Chicago", 60601));
        getAddress(employeeAddressMap, new Employee(3, "Abhi"));
        Set<String> distinctCities = new TreeSet<>();
        for (Address address : employeeAddressMap.values()) {
            distinctCities.add(address.getCity());
        }
        System.out.println("Distinct cities in ascending order: " + distinctCities);
    }
    public static void getAddress(Map<Employee, Address> employeeAddressMap, Employee employee) {
        Address address = employeeAddressMap.get(employee);
        if (address != null) {
            System.out.println("House Number : "+address.getHouseNumber() + "\nStreet Name : " + address.getStreetName() + "\nCity : " + address.getCity() + "\nPincode : " + address.getPinCode());
        }
        else {
            System.out.println("Employee does not exist");
        }
    }
}