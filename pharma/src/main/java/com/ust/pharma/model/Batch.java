package com.ust.pharma.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * BatchInfo entity class
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "batch_info")
public class Batch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String batchCode;
    private BigDecimal weight;
    private BigDecimal price;
    private BigDecimal shippingCharge;
    private String careLevel;
    private String medicineTypeCode;
    private String medicineCode;

}
