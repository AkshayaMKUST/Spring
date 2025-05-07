package org.example;

import org.example.exception.DuplicateAddressException;
import org.example.exception.NoEntriesException;
import org.example.exception.NoEntryException;
import org.example.model.Address;
import org.example.service.AddressImpl;

public class Main {
    public static void main(String[] args) {
        AddressImpl addressImpl = new AddressImpl();
        try {
            Address address1 = new Address("Akshaya","M K","IV","M V Street",670046,"Kerala","Kannur");
            addressImpl.addAddress(address1);

            Address address2 = new Address("Jane", "Doe", "456 Elm St", "Town", 670014, "Kerala", "Kannur");
            addressImpl.addAddress(address2);

            addressImpl.removeAddress("Jane");

            addressImpl.removeAddress("Alice");
        } catch (DuplicateAddressException | NullPointerException | NoEntriesException | NoEntryException e) {
            System.out.println("Error : "+e.getMessage());
        }
    }
}