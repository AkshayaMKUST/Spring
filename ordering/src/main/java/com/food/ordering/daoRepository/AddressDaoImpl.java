package com.food.ordering.daoRepository;

import com.food.ordering.exception.DuplicateAddressException;
import com.food.ordering.exception.NoEntriesException;
import com.food.ordering.exception.NoEntryException;
import com.food.ordering.model.Address;
import com.food.ordering.model.AddressId;
import com.food.ordering.repository.IAddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class AddressDaoImpl implements IAddressDaoRepository {
    private Set<Address> addressBook=new HashSet<>();

    @Autowired
    IAddressBookRepository iAddressBookRepository;

    /**
     * @param address
     * @return Address
     * @throws DuplicateAddressException
     */
    @Override
    public Address addAddress(Address address) throws DuplicateAddressException {
        boolean addAddressFlag = addressBook.add(address);
        if(!addAddressFlag){
            throw new DuplicateAddressException("Address might be duplicated");
        }
        return iAddressBookRepository.save(address);
    }


    /**
     * @param addressId
     * @return Address
     * @throws NoEntryException
     */
    @Override
    public Address getAddressById(AddressId addressId) throws NoEntryException {
        Optional<Address> optionalAddress = iAddressBookRepository.findById(addressId);
        Address fetchedAddress = optionalAddress.get();
        if(optionalAddress.isEmpty()){
            throw new NoEntryException("Address not found");
        }
        return fetchedAddress;
    }

    /**
     * @return LIst<Address>
     * @throws NoEntriesException
     */
    @Override
    public List<Address> getAllAddress() throws NoEntriesException {
        if(addressBook.isEmpty()){
            throw new NoEntriesException("No addresses");
        }
        return iAddressBookRepository.findAll();
    }

    /**
     * @param address
     * @return Address
     * @throws NoEntryException
     */
    @Override
    public Address updateAddress(Address address) throws NoEntryException {
        Optional<Address> optionalAddress = iAddressBookRepository.findById(address.getAddressId());
        if (optionalAddress.isPresent()){
            Address updatedAddress = optionalAddress.get();
            updatedAddress.setFlatId(address.getFlatId());
            updatedAddress.setStreetName(address.getStreetName());
            updatedAddress.setCity(address.getCity());
            updatedAddress.setStateName(address.getStateName());
            updatedAddress.setPinCode(address.getPinCode());
            addressBook.remove(optionalAddress);
            addressBook.add(updatedAddress);
            return updatedAddress;
        }
        throw new NoEntryException("Address not found");
    }

    /**
     * @param addressId
     * @return String
     * @throws NoEntryException
     * @throws NoEntriesException
     */
    @Override
    public String removeAddress(AddressId addressId) throws NoEntryException, NoEntriesException {
        if (addressBook.isEmpty()){
            throw new NoEntriesException("No Entries of addresses");
        }
        Optional<Address> optionalAddress = iAddressBookRepository.findById(addressId);
        if (optionalAddress.isEmpty()){
            throw new NoEntryException("No entry found");
        }
        iAddressBookRepository.deleteById(addressId);
        return "The address is deleted";
    }
}
