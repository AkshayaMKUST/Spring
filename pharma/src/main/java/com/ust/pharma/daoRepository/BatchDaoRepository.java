package com.ust.pharma.daoRepository;

import com.ust.pharma.dto.BatchVO;
import com.ust.pharma.exception.PharmaBusinessException;
import com.ust.pharma.exception.PharmaException;
import com.ust.pharma.model.Batch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BatchDaoRepository {
    /**
     * adding the new batch to database
     * @param batch
     * @return boolean true/false
     * @throws PharmaException
     */
    public boolean addDetails(Batch batch) throws PharmaException;

    /**
     * method to get the shipping charge from the database
     * @param medicineTypeCode
     * @param weightRange
     * @return BigDecimal
     * @throws PharmaBusinessException
     */
    public BigDecimal getShippingCharge(String medicineTypeCode, String weightRange) throws PharmaBusinessException;

    /**
     * checks if the medicine code already exist in database
     * @param medicineCode
     * @return boolean true/false
     */
    public boolean checkMedicineCode(String medicineCode);

    /**
     * checks if the batch code already exist in database
     * @param batchCode
     * @return boolean true/false
     */
    public boolean checkBatchCode(String batchCode);



    /**
     * @param batchCode
     * @return Batch
     */
    Batch fetchBatchById(String batchCode) throws PharmaException;

    /**
     * @param pageable
     * @return Page
     */
    Page<Batch> fetchAllBatch(Pageable pageable);
}
