package org.example.service;

import org.example.exception.DuplicateAddressException;
import org.example.exception.NoEntriesException;
import org.example.exception.NoEntryException;
import org.example.model.Address;

public interface IAddress {
    public boolean addAddress(Address address) throws DuplicateAddressException, NullPointerException;
    public boolean removeAddress(String firstName) throws NoEntriesException, NoEntryException, NullPointerException ;

    }
