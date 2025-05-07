package com.food.ordering.controller;

import com.food.ordering.exception.DuplicateAddressException;
import com.food.ordering.exception.NoEntriesException;
import com.food.ordering.exception.NoEntryException;
import com.food.ordering.model.Address;
import com.food.ordering.model.AddressId;
import com.food.ordering.service.IAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressBookController {
    @Autowired
    IAddressBookService iAddressBookService;

    @PostMapping
    public Address addAddress(@RequestBody Address address) throws DuplicateAddressException {
        return iAddressBookService.addAddress(address);
    }

    /**
     * @param firstName
     * @param lastName
     * @return ResponseEntity<Address>
     * @throws NoEntryException
     */
    @GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<Address> getAddressById(@PathVariable String firstName, @PathVariable String lastName) throws NoEntryException, NoEntryException {
        AddressId addressId = new AddressId(firstName, lastName);
        Address fetchedAddress= iAddressBookService.getAddressById(addressId);
        return new ResponseEntity<>(fetchedAddress, HttpStatus.OK);
    }

    /**
     * @return ResponseEntity<List<Address>>
     * @throws NoEntriesException
     */
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddress() throws NoEntriesException{
        List<Address> fetchedAllAddress = iAddressBookService.getAllAddress();
        return new ResponseEntity<>(fetchedAllAddress,HttpStatus.OK);
    }

    /**
     * @param address
     * @return Address
     * @throws NoEntryException
     */
    @PutMapping
    public ResponseEntity<Address> updateAddress(Address address) throws NoEntryException {
        Address updatedAddress = iAddressBookService.updateAddress(address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<String> removeAddress(@PathVariable String firstName, @PathVariable String lastName) throws NoEntriesException, NoEntryException {
        AddressId addressId = new AddressId(firstName, lastName);
        String response = iAddressBookService.removeAddress(addressId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
