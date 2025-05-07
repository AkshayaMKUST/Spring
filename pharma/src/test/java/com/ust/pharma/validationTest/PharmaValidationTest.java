package com.ust.pharma.validationTest;
import com.ust.pharma.daoRepository.BatchDAO;
import com.ust.pharma.dto.BatchVO;
import com.ust.pharma.exception.PharmaBusinessException;
import com.ust.pharma.validation.PharmaValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PharmaValidationTest {

    @Mock
    private BatchDAO batchDAO;

    @InjectMocks
    private PharmaValidation pharmaValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testValidateBatchVO_MedicineCode(){
        BatchVO invalidBatchVO = new BatchVO();
        invalidBatchVO.setMedicineCode(null);
        assertThrows(PharmaBusinessException.class, () ->
                pharmaValidation.validateBatchVO(invalidBatchVO));
    }

    @Test
    public void testValidateBatchVO_ExistingBatchCode() {
        BatchVO existingBatchCodeBatchVO = new BatchVO();
        existingBatchCodeBatchVO.setBatchCode("BTC-1234");
        when(batchDAO.checkBatchCode(existingBatchCodeBatchVO.getBatchCode())).thenReturn(true);
        when(batchDAO.checkMedicineCode(existingBatchCodeBatchVO.getMedicineCode())).thenReturn(true);
        assertThrows(PharmaBusinessException.class, () -> pharmaValidation.validateBatchVO(existingBatchCodeBatchVO));
    }

    @Test
    public void testValidateBatchVO_CheckBatchCodePattern(){
        BatchVO batchCodePattern = new BatchVO();
        batchCodePattern.setBatchCode("BC-1234");
        batchCodePattern.setMedicineCode("MC_301");
        batchCodePattern.setWeight(BigDecimal.valueOf(150));
        when(batchDAO.checkMedicineCode(batchCodePattern.getMedicineCode())).thenReturn(true);
        when(batchDAO.checkBatchCode(batchCodePattern.getBatchCode())).thenReturn(false);
        assertThrows(PharmaBusinessException.class, () ->
                pharmaValidation.validateBatchVO(batchCodePattern));
        verify(batchDAO, times(1)).checkBatchCode(batchCodePattern.getBatchCode());
    }

    @Test
    public void testValidateBatchVO_CheckWeight() {
        BatchVO batchWeight = new BatchVO();
        batchWeight.setBatchCode("BTC-1234");
        batchWeight.setMedicineCode("MC_301");
        batchWeight.setWeight(BigDecimal.valueOf(50));
        when(batchDAO.checkMedicineCode(batchWeight.getMedicineCode())).thenReturn(true);
        when(batchDAO.checkBatchCode(batchWeight.getBatchCode())).thenReturn(false);
        assertThrows(PharmaBusinessException.class, () -> pharmaValidation.validateBatchVO(batchWeight));
        verify(batchDAO, times(1)).checkBatchCode(batchWeight.getBatchCode());
    }

    @Test
    public void testValidateBatchVO_MissingMandatoryField() {
        BatchVO invalidBatchVO = new BatchVO();
        invalidBatchVO.setBatchCode("BTC-1234");
        invalidBatchVO.setMedicineCode(null);
        invalidBatchVO.setWeight(BigDecimal.valueOf(150));
        invalidBatchVO.setPrice(BigDecimal.valueOf(50));
        invalidBatchVO.setRefrigeration("yes");
        assertThrows(PharmaBusinessException.class, () ->
                pharmaValidation.validateFieldsMandatory(invalidBatchVO));
    }
}
