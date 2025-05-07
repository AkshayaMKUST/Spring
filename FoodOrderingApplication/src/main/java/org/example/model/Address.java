package org.example.model;

public class Address {
    public String firstName;
    public String lastName;
    public String flatId;
    public String streetName;
    public Integer pinCode;
    public String stateName;
    public String city;

    public Address(String firstName, String lastName, String flatId, String streetName, Integer pinCode, String stateName, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.flatId = flatId;
        this.streetName = streetName;
        this.pinCode = pinCode;
        this.stateName = stateName;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFlatId() {
        return flatId;
    }

    public void setFlatId(String flatId) {
        this.flatId = flatId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
