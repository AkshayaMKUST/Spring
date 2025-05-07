package com.ust.pharma.repository;

import com.ust.pharma.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BatchInfo Repository
 */
@Repository
public interface IBatchRepository extends JpaRepository<Batch,String> {
}