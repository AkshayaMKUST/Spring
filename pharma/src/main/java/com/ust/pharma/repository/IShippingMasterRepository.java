package com.ust.pharma.repository;

import com.ust.pharma.model.ShippingMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShippingMasterRepository extends JpaRepository<ShippingMaster,String> {
    /**
     * @param medicineType
     * @param weightRange
     * @return ShippingMaster
     */
    ShippingMaster findByMedicineTypeCodeAndWeightRange(String medicineType, String weightRange);
}
