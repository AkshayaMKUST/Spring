package com.ust.pharma.service.serviceImpl;

import com.ust.pharma.constants.CommonConstants;
import com.ust.pharma.daoRepository.BatchDaoRepository;
import com.ust.pharma.dto.BatchVO;
import com.ust.pharma.model.Batch;
import com.ust.pharma.exception.PharmaBusinessException;
import com.ust.pharma.exception.PharmaException;
import com.ust.pharma.service.IPharmaService;
import com.ust.pharma.validation.PharmaValidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Pharma Service Implementation of {@link IPharmaService}
 */
@Service
public class PharmaServiceImpl implements IPharmaService {

    private Logger logger = LoggerFactory.getLogger(PharmaServiceImpl.class);

    @Autowired
    private BatchDaoRepository batchDaoRepository;

    @Autowired
    private PharmaValidation pharmaValidation;

    /**
     * add new batch after validation
     * @param batchVO
     * @return boolean true/false
     * @throws PharmaException
     * @throws PharmaBusinessException
     */
    @Override
    public boolean addBatch(BatchVO batchVO) throws PharmaException, PharmaBusinessException {
        logger.info("Invoked service method addBatch"+batchVO);
        pharmaValidation.validateFieldsMandatory(batchVO);
        pharmaValidation.validateBatchVO(batchVO);
        BigDecimal shippingCharges = calculateShippingCharges(batchVO);
        Batch batch = new Batch();
        batch.setBatchCode(batchVO.getBatchCode());
        batch.setWeight(batchVO.getWeight());
        batch.setPrice(batchVO.getPrice());
        batch.setShippingCharge(shippingCharges);
        batch.setMedicineCode(batchVO.getMedicineCode());
        batch.setMedicineTypeCode(batchVO.getMedicineTypeCode());
        batch.setCareLevel(generateCareLevel(batchVO.getMedicineTypeCode()));
        logger.info("Method Exit method_name addBatch :" + batchDaoRepository.addDetails(batch));
        return batchDaoRepository.addDetails(batch);
    }


//    @Override
//    public List<Batch> fetchAllBatch() {
//        return batchDaoRepository.fetchAllBatch();
//    }

    /**
     * Fetch batch by batch code
     * @param batchCode
     * @return BatchVO
     * @throws PharmaException
     */
    @Override
    public Batch fetchBatchById(String batchCode) throws PharmaException {
        logger.info("Invoked service method fetchBatchById for batchCode: " + batchCode);
        return batchDaoRepository.fetchBatchById(batchCode);
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Batch> fetchAllBatch(Pageable pageable) {
        logger.info("Invoked service method fetchAllBatch");
        Page<Batch> batchPage = batchDaoRepository.fetchAllBatch(pageable);
        return batchPage;
    }


    /**
     * determining weight range
     * @param weight
     * @return String
     */
    public String determineWeightRange(BigDecimal weight) {
        if (weight.compareTo(BigDecimal.valueOf(500)) <= 0) {
            return CommonConstants.WEIGHT_ONE;
        } else if (weight.compareTo(BigDecimal.valueOf(1000)) <= 0) {
            return CommonConstants.WEIGHT_TWO;
        } else {
            return CommonConstants.WEIGHT_THREE;
        }
    }
    /**
     * calculate the shipping charges for the batch
     * @param batchVO
     * @return BigDecimal
     * @throws PharmaBusinessException
     */
    public BigDecimal calculateShippingCharges(BatchVO batchVO) throws PharmaBusinessException {

        logger.info("Invoked method calculateShippingCharges "+ batchVO);
        BigDecimal weight = batchVO.getWeight();
        String medicineType = batchVO.getMedicineTypeCode();
        BigDecimal shippingCharge;

        String weightRange = determineWeightRange(weight);
        BigDecimal actualShippingCharge = batchDaoRepository.getShippingCharge(medicineType,weightRange);
        if (actualShippingCharge != null) {
            if (batchVO.getRefrigeration().equalsIgnoreCase("yes")) {
                BigDecimal refrigerationCharge = actualShippingCharge.multiply(BigDecimal.valueOf(0.05));
                shippingCharge = actualShippingCharge.add(refrigerationCharge);
            } else {
                shippingCharge = actualShippingCharge;
            }
            logger.info("Method Exit method_name : calculateShippingCharges " + shippingCharge);
            return shippingCharge;
        } else {
            throw new PharmaBusinessException("Actual shipping charge is null");
        }
    }

    /**
     * generate the care level message
     * @param medicineType
     * @return
     */
    public String generateCareLevel(String medicineType){
        logger.info("Invoked method generateCareLevel "+medicineType);
        if (medicineType.equalsIgnoreCase("C")){
            logger.debug("Method Exit method_name generateCareLevel: " +CommonConstants.NORMAL_CARE_LEVEL);
            return CommonConstants.NORMAL_CARE_LEVEL;
        } else if (medicineType.equalsIgnoreCase("T")) {
            logger.debug("Method Exit method_name generateCareLevel: " +CommonConstants.HIGH_CARE_LEVEL);
            return CommonConstants.HIGH_CARE_LEVEL;
        }else {
            logger.debug("Method Exit method_name generateCareLevel: "+CommonConstants.EXTREMELY_HIGH_CARE_LEVEL);
            return CommonConstants.EXTREMELY_HIGH_CARE_LEVEL;
        }
    }
}
