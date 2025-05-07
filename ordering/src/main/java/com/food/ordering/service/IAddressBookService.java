package com.food.ordering.service;

import com.food.ordering.exception.DuplicateAddressException;
import com.food.ordering.exception.NoEntriesException;
import com.food.ordering.exception.NoEntryException;
import com.food.ordering.model.Address;
import com.food.ordering.model.AddressId;

import java.util.List;

public interface IAddressBookService {
    public Address addAddress(Address address) throws DuplicateAddressException;

    /**
     * @param addressId
     * @return Address
     * @throws NoEntryException
     */
    public Address getAddressById(AddressId addressId) throws NoEntryException;

    /**
     * @return List<Address>
     * @throws NoEntriesException
     */
    public List<Address> getAllAddress() throws NoEntriesException;

    /**
     * @param address
     * @return Address
     */
    public Address updateAddress(Address address) throws NoEntryException;

    /**
     * @param addressId
     * @return String
     */
    public String removeAddress(AddressId addressId) throws NoEntryException, NoEntriesException;
}
