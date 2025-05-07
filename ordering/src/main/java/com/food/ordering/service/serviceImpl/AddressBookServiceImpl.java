package com.food.ordering.service.serviceImpl;

import com.food.ordering.daoRepository.IAddressDaoRepository;
import com.food.ordering.exception.DuplicateAddressException;
import com.food.ordering.exception.NoEntriesException;
import com.food.ordering.exception.NoEntryException;
import com.food.ordering.model.Address;
import com.food.ordering.model.AddressId;
import com.food.ordering.service.IAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressBookServiceImpl implements IAddressBookService {

    @Autowired
    IAddressDaoRepository iAddressDaoRepository;

    /**
     * @param address
     * @return Address
     */
    @Override
    public Address addAddress(Address address) throws DuplicateAddressException {
        return iAddressDaoRepository.addAddress(address);
    }

    /**
     * @param addressId
     * @return Address
     * @throws NoEntryException
     */
    @Override
    public Address getAddressById(AddressId addressId) throws NoEntryException {
        return iAddressDaoRepository.getAddressById(addressId);
    }

    /**
     * @return List<Address>
     * @throws NoEntriesException
     */
    @Override
    public List<Address> getAllAddress() throws NoEntriesException {
        return iAddressDaoRepository.getAllAddress();
    }

    /**
     * @param address
     * @return Address
     */
    @Override
    public Address updateAddress(Address address) throws NoEntryException {
        return iAddressDaoRepository.updateAddress(address);
    }

    /**
     * @param addressId
     * @return String
     */
    @Override
    public String removeAddress(AddressId addressId) throws NoEntryException, NoEntriesException {
        return iAddressDaoRepository.removeAddress(addressId);
    }

}
