package com.food.ordering.repository;

import com.food.ordering.model.Address;
import com.food.ordering.model.AddressId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressBookRepository extends JpaRepository<Address, AddressId> {
}
