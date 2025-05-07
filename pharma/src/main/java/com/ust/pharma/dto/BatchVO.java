package com.ust.pharma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Value Object class
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchVO {

    private String batchCode;
    private String medicineCode;
    private BigDecimal weight;
    private BigDecimal price;
    private String medicineTypeCode;
    private String refrigeration;
}

