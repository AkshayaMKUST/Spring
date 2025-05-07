package org.employee;

public class Address {
    int houseNumber;
    String streetName;
    String city;
    int pinCode;

    public Address(int houseNumber, String streetName, String city, int pinCode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.city = city;
        this.pinCode = pinCode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }



    public String getStreetName() {
        return streetName;
    }



    public String getCity() {
        return city;
    }

    public int getPinCode() {
        return pinCode;
    }


}
