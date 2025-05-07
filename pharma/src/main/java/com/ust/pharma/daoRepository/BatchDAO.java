package com.ust.pharma.daoRepository;

import com.ust.pharma.constants.CommonConstants;
import com.ust.pharma.exception.PharmaBusinessException;
import com.ust.pharma.exception.PharmaException;
import com.ust.pharma.model.Batch;
import com.ust.pharma.model.MedicineMaster;
import com.ust.pharma.model.ShippingMaster;
import com.ust.pharma.repository.IBatchRepository;
import com.ust.pharma.repository.IMedicineMasterRepository;
import com.ust.pharma.repository.IShippingMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class BatchDAO  implements BatchDaoRepository{

    private Logger logger = LoggerFactory.getLogger(BatchDAO.class);

    @Autowired
    IBatchRepository iBatchRepository;

    @Autowired
    IShippingMasterRepository iShippingMasterRepository;

    @Autowired
    IMedicineMasterRepository iMedicineMasterRepository;

    /**
     * adding the new batch to database
     * @param batch
     * @return boolean true/false
     * @throws PharmaException
     */
    public boolean addDetails(Batch batch) throws PharmaException {
        logger.info("Invoked method addDetails"+ batch);
        try{
            iBatchRepository.save(batch);
            logger.info("Method Exit method_name addDetails:" +true);
            return true;
        }catch (Exception e){
            logger.error(CommonConstants.ERROR_MESSAGE);
            throw new PharmaException(CommonConstants.ERROR_MESSAGE);
        }
    }

    /**
     * method to get the shipping charge from the database
     * @param medicineTypeCode
     * @param weightRange
     * @return BigDecimal
     * @throws PharmaBusinessException
     */
    public BigDecimal getShippingCharge(String medicineTypeCode, String weightRange) throws PharmaBusinessException {
        logger.info("Invoked method getShippingCharge : Parameter 1: "+ medicineTypeCode + " Parameter 2: "+ weightRange);
        ShippingMaster shippingMaster = iShippingMasterRepository.findByMedicineTypeCodeAndWeightRange(medicineTypeCode,weightRange);
        if(shippingMaster!=null){
            logger.info("Method Exit method_name getShippingCharge :" +shippingMaster.getShippingCharge());
            return shippingMaster.getShippingCharge();
        }else{
            logger.error("Shipping charge not found for batch code ");
            throw new PharmaBusinessException("Shipping charge not found for batch code " );
        }
    }

    /**
     * checks if the medicine code already exist in database
     * @param medicineCode
     * @return boolean true/false
     */
    public boolean checkMedicineCode(String medicineCode){
        logger.info("Invoked method isValidMedicineCode : "+ medicineCode);
        Optional<MedicineMaster> medicineMasterOptional = iMedicineMasterRepository.findById(medicineCode);
        logger.info("Method Exit method_name isValidMedicineCode :" + medicineMasterOptional.isPresent());
        return medicineMasterOptional.isPresent();
    }

    /**
     * checks if the batch code already exist in database
     * @param batchCode
     * @return boolean true/false
     */
    public boolean checkBatchCode(String batchCode){
        logger.info("Invoked method isBatchCodeExists : "+ batchCode);
        Optional<Batch> batchInfoOptional = iBatchRepository.findById(batchCode);
        logger.info("Method Exit method_name isBatchCodeExists :" + batchInfoOptional.isPresent());
        return batchInfoOptional.isPresent();
    }

    /**
     * @return List
     */
//    @Override
//    public List<Batch> fetchAllBatch() {
//        return iBatchRepository.findAll();
//    }

    /**
     * @param batchCode
     * @return
     */
    @Override
    public Batch fetchBatchById(String batchCode) throws PharmaException {
        try {
            Optional<Batch> batchOptional = iBatchRepository.findById(batchCode);

            if (batchOptional.isPresent()) {
                return batchOptional.get();
            }
            else {
                logger.error("Batch not found for batch code: " + batchCode);
                throw new PharmaException("Batch not found for batch code: " + batchCode);
            }

        }
        catch (Exception e){
            logger.error(CommonConstants.BATCH_NOT_FOUND);
            throw new PharmaException(CommonConstants.BATCH_NOT_FOUND);
        }
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Batch> fetchAllBatch(Pageable pageable) {
        return iBatchRepository.findAll(pageable);
    }
}
