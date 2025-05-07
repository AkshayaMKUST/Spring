package com.ust.pharma.repository;

import com.ust.pharma.model.MedicineMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicineMasterRepository extends JpaRepository<MedicineMaster,String> {
}
