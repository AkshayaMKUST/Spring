package com.ust.pharma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Shipping Master Entity class
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "shipping_master")
public class ShippingMaster {

    @Id
    private String medicineTypeCode;
    private String weightRange;
    private BigDecimal shippingCharge;
}