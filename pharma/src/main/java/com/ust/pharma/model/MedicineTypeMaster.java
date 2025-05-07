package com.ust.pharma.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Medicine Type Master Entity class
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "medicine_type_master")
public class MedicineTypeMaster {
    @Id
    private String medicineTypeCode;
    private String medicineTypeName;
}
