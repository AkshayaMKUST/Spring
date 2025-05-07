package com.ust.pharma.repository;

import com.ust.pharma.model.MedicineTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicineTypeMasterRepository extends JpaRepository<MedicineTypeMaster,String> {
}
