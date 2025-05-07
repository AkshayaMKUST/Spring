package org.example.service;

import org.example.exception.DuplicateAddressException;
import org.example.exception.NoEntriesException;
import org.example.exception.NoEntryException;
import org.example.model.Address;

import java.util.HashSet;
import java.util.Set;

public class AddressImpl implements IAddress {

    private Set<Address> addressBook = new HashSet<>();
    public AddressImpl() {
    }

    @Override
    public boolean addAddress(Address address) throws DuplicateAddressException, NullPointerException {
        final String METHOD_NAME = "addAddress";
        System.out.println("Method Invoked:" + this.getClass().getName() + ":" + METHOD_NAME + ":" + address);
        if (address == null) {
            throw new NullPointerException("Address Reference Is Null");
        }
        boolean addAddressFlag = addressBook.add(address);
        if (!addAddressFlag) {
            throw new DuplicateAddressException("Address Might Be Duplicated !!! Have A Look Into");
        }
        System.out.println("Method Response:" + this.getClass().getName() + ":" + addAddressFlag);
        return addAddressFlag;
    }
    @Override
    public boolean removeAddress(String firstName) throws NoEntriesException, NoEntryException, NullPointerException {
        final String METHOD_NAME = "removeAddress";
        System.out.println("Method Invoked:" + this.getClass().getName() + ":" + METHOD_NAME + ":" + firstName);
        if (firstName == null || firstName.isEmpty() || firstName.isBlank()) {
            throw new NullPointerException("First Name Either Null||Empty||Blank Do Check!!!");
        }
        if (addressBook.isEmpty()) {
            throw new NoEntriesException("No Entries So Far In The Address Book!!!");
        } else {
            boolean addressRemoved = addressBook.removeIf(address -> firstName.equals(address.getFirstName()));
            if (!addressRemoved) {
                throw new NoEntryException("No entry found with the given first name: " + firstName);
            }
            System.out.println("Method Response:" + this.getClass().getName() + ":" + addressRemoved);
            return addressRemoved;
        }
    }
}
