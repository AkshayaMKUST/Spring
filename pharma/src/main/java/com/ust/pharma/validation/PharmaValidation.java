package com.ust.pharma.validation;

import com.ust.pharma.daoRepository.BatchDAO;
import com.ust.pharma.dto.BatchVO;
import com.ust.pharma.exception.PharmaBusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Validation of new batch info before adding the batch
 */
@Component
public class PharmaValidation {

    private Logger logger = LoggerFactory.getLogger(PharmaValidation.class);

    @Autowired
    BatchDAO batchDAO;

    /**
     * validating the batchVO
     * @param batchVO
     * @throws PharmaBusinessException
     */
    public void validateBatchVO(BatchVO batchVO) throws PharmaBusinessException {

        logger.info("Invoked method validateBatchVO "+ batchVO);
        if (!batchDAO.checkMedicineCode(batchVO.getMedicineCode())) {
            logger.error("Medicine code does not exist");
            throw new PharmaBusinessException("Medicine code does not exist", 510);
        }

        if (batchDAO.checkBatchCode(batchVO.getBatchCode())) {
            logger.error("Batch code already exists");
            throw new PharmaBusinessException("Batch code already exists", 511);
        }

        if (batchVO.getWeight().compareTo(BigDecimal.valueOf(100)) < 0) {
            logger.error("Batch weight should be greater than 100");
            throw new PharmaBusinessException("Batch weight should be greater than 100", 512);
        }

        if (!batchVO.getBatchCode().matches("^BTC-\\d{4}$")) {
            logger.error("Batch code format is wrong. It should be in the format \"BTC-1234\"");
            throw new PharmaBusinessException("Batch code format is wrong. It should be in the format \"BTC-1234\"", 513);
        }

    }

    /**
     * validate all the fields are filled
     * @param batchVO
     * @throws PharmaBusinessException
     */
    public void validateFieldsMandatory(BatchVO batchVO) throws PharmaBusinessException {
        logger.info("Invoked method validateMandatoryFields "+ batchVO);
        if (batchVO.getBatchCode() == null || batchVO.getWeight() == null || batchVO.getPrice() == null ||
                batchVO.getMedicineTypeCode() == null || batchVO.getMedicineCode() == null) {
            logger.error("All fields are mandatory");
            throw new PharmaBusinessException("All fields are mandatory");
        }
    }
}
