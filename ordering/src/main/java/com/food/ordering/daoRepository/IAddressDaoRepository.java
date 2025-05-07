package com.food.ordering.daoRepository;

import com.food.ordering.exception.DuplicateAddressException;
import com.food.ordering.exception.NoEntriesException;
import com.food.ordering.exception.NoEntryException;
import com.food.ordering.model.Address;
import com.food.ordering.model.AddressId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAddressDaoRepository {
    /**
     * @param address
     * @return Address
     * @throws DuplicateAddressException
     */
    public Address addAddress(Address address) throws DuplicateAddressException;

    /**
     * @param addressId
     * @return Address
     * @throws NoEntryException
     */
    public Address getAddressById(AddressId addressId) throws NoEntryException;

    /**
     * @return LIst<Address>
     * @throws NoEntriesException
     */
    public List<Address> getAllAddress() throws NoEntriesException;

    /**
     * @param address
     * @return Address
     * @throws NoEntryException
     */
    public Address updateAddress(Address address) throws NoEntryException;

    /**
     * @param addressId
     * @return String
     * @throws NoEntryException
     * @throws NoEntriesException
     */
    public String removeAddress(AddressId addressId) throws NoEntryException, NoEntriesException;
}
