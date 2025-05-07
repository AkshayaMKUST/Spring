package com.ust.pharma.service;

import com.ust.pharma.dto.BatchVO;
import com.ust.pharma.exception.PharmaBusinessException;
import com.ust.pharma.exception.PharmaException;
import com.ust.pharma.model.Batch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Pharma Service Interface
 */
public interface IPharmaService {
    /**
     * add new batch after validation
     * @param batchVO
     * @return boolean true/false
     * @throws PharmaBusinessException
     * @throws PharmaException
     */
    boolean addBatch(BatchVO batchVO)throws PharmaBusinessException, PharmaException;

//    List<Batch> fetchAllBatch();

    Batch fetchBatchById(String batchCode) throws PharmaException;

    Page<Batch> fetchAllBatch(Pageable pageable);
}
