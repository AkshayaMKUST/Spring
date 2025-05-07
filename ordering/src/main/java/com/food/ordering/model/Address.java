package com.food.ordering.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @EmbeddedId
    public AddressId addressId;
    private Integer flatId;
    public String streetName;
    public Integer pinCode;
    public String stateName;
    public String city;

}
