package com.ust.pharma.serviceImplTest;

import com.ust.pharma.constants.CommonConstants;
import com.ust.pharma.daoRepository.BatchDaoRepository;
import com.ust.pharma.dto.BatchVO;
import com.ust.pharma.exception.PharmaBusinessException;
import com.ust.pharma.exception.PharmaException;
import com.ust.pharma.model.Batch;
import com.ust.pharma.repository.IBatchRepository;
import com.ust.pharma.repository.IMedicineMasterRepository;
import com.ust.pharma.repository.IShippingMasterRepository;
import com.ust.pharma.service.serviceImpl.PharmaServiceImpl;
import com.ust.pharma.validation.PharmaValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PharmaServiceImplTest {

    @Mock
    private IBatchRepository batchRepository;

    @Mock
    private IShippingMasterRepository shippingMasterRepository;

    @Mock
    private IMedicineMasterRepository medicineMasterRepository;

    @InjectMocks
    private PharmaServiceImpl pharmaService;

    @Mock
    private BatchDaoRepository batchDaoRepository;

    @Mock
    private PharmaValidation pharmaValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddBatch_Success() throws PharmaException, PharmaBusinessException {
        BatchVO batchVO = new BatchVO();
        batchVO.setBatchCode("BTC_1200");
        batchVO.setWeight(BigDecimal.valueOf(1000));
        batchVO.setPrice(BigDecimal.valueOf(500));
        batchVO.setMedicineCode("MC_301");
        batchVO.setMedicineTypeCode("C");
        batchVO.setRefrigeration("yes");
        doNothing().when(pharmaValidation).validateFieldsMandatory(batchVO);
        doNothing().when(pharmaValidation).validateBatchVO(batchVO);
        when(batchDaoRepository.getShippingCharge(anyString(), anyString())).thenReturn(BigDecimal.TEN);
        when(batchDaoRepository.addDetails(any(Batch.class))).thenReturn(true);
        boolean result = pharmaService.addBatch(batchVO);
        assertTrue(result);
        verify(pharmaValidation, times(1)).validateFieldsMandatory(batchVO);
        verify(pharmaValidation, times(1)).validateBatchVO(batchVO);
        verify(batchDaoRepository, times(2)).addDetails(any(Batch.class));
    }

    @Test
    public void testCalculateShippingCharges_RefrigerationYes() throws PharmaBusinessException {
        BatchVO batchVO = new BatchVO();
        batchVO.setWeight(BigDecimal.valueOf(1000));
        batchVO.setMedicineTypeCode("C");
        batchVO.setRefrigeration("yes");
        BigDecimal actualShippingCharge = BigDecimal.valueOf(50); // Example actual shipping charge
        when(batchDaoRepository.getShippingCharge(anyString(), anyString())).thenReturn(actualShippingCharge);
        BigDecimal result = pharmaService.calculateShippingCharges(batchVO);
        BigDecimal expectedShippingCharge = BigDecimal.valueOf(52.5);
        assertEquals(0, expectedShippingCharge.compareTo(result));

    }
    @Test
    public void testCalculateShippingCharges_NoRefrigeration() throws PharmaBusinessException {
        BatchVO batchVO = new BatchVO();
        batchVO.setWeight(BigDecimal.valueOf(1000));
        batchVO.setMedicineTypeCode("C");
        batchVO.setRefrigeration("no");
        BigDecimal actualShippingCharge = BigDecimal.valueOf(50); // Example value
        when(batchDaoRepository.getShippingCharge(anyString(), anyString())).thenReturn(actualShippingCharge);
        BigDecimal result = pharmaService.calculateShippingCharges(batchVO);
        assertEquals(0,actualShippingCharge.compareTo(result));
    }
    @Test
    public void testCalculateShippingCharges_NullActualShippingCharge() throws PharmaBusinessException {
        BatchVO batchVO = new BatchVO();
        batchVO.setWeight(BigDecimal.valueOf(1000));
        batchVO.setMedicineTypeCode("C");
        batchVO.setRefrigeration("yes");
        when(batchDaoRepository.getShippingCharge(anyString(), anyString())).thenReturn(null);
//        Mockito.when(pharmaService.calculateShippingCharges(Mockito.any())).thenThrow(Exception.class);
        PharmaBusinessException exception = assertThrows(PharmaBusinessException.class, () -> {
            pharmaService.calculateShippingCharges(batchVO);
        });
        assertEquals("Actual shipping charge is null", exception.getMessage());
    }

    @Test
    public void testCheckBatchCode_NotExists() {
        String batchCode = "BATCH001";
        when(batchRepository.findById(batchCode)).thenReturn(Optional.empty());
        boolean result = batchDaoRepository.checkBatchCode(batchCode);
        assertFalse(result);
    }

    @Test
    public void testDetermineWeightRange(){
        assertEquals(CommonConstants.WEIGHT_ONE,pharmaService.determineWeightRange(BigDecimal.valueOf(500)));
        assertEquals(CommonConstants.WEIGHT_TWO,pharmaService.determineWeightRange(BigDecimal.valueOf(1000)));
        assertEquals(CommonConstants.WEIGHT_THREE,pharmaService.determineWeightRange(BigDecimal.valueOf(1500)));

    }
    @Test
    public void testGenerateCareLevel_Normal() {
        String medicineType = "C";
        String expectedCareLevel = CommonConstants.NORMAL_CARE_LEVEL;
        String result = pharmaService.generateCareLevel(medicineType);
        assertEquals(expectedCareLevel, result);
    }

    @Test
    public void testGenerateCareLevel_High() {
        String medicineType = "T";
        String expectedCareLevel = CommonConstants.HIGH_CARE_LEVEL;
        String result = pharmaService.generateCareLevel(medicineType);
        assertEquals(expectedCareLevel, result);
    }

    @Test
    public void testGenerateCareLevel_ExtremelyHigh() {
        String medicineType = "S";
        String expectedCareLevel = CommonConstants.EXTREMELY_HIGH_CARE_LEVEL;
        String result = pharmaService.generateCareLevel(medicineType);
        assertEquals(expectedCareLevel, result);
    }

    @Test
    public void testFetchBatchById() throws PharmaException {
        String batchCode = "BTC-1234";
        Batch batch = new Batch();
        batch.setBatchCode(batchCode);
        when(batchDaoRepository.fetchBatchById(batchCode)).thenReturn(batch);
        Batch resultBatch = pharmaService.fetchBatchById(batchCode);
        assertEquals(batch, resultBatch);
        verify(batchDaoRepository, times(1)).fetchBatchById(batchCode);
    }

    @Test
    public void testFetchAllBatch_Success() {
        Page<Batch> page = null;
        Pageable pageable = null;
        when(batchDaoRepository.fetchAllBatch(any())).thenReturn(page);
        Page<Batch> resultPage = pharmaService.fetchAllBatch(pageable);
        assertEquals(page, resultPage);
        verify(batchDaoRepository, times(1)).fetchAllBatch(pageable);
    }
}

