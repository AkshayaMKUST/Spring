package com.ust.pharma.controllerTest;
import com.ust.pharma.controller.PharmaController;
import com.ust.pharma.dto.BatchVO;
import com.ust.pharma.exception.PharmaBusinessException;
import com.ust.pharma.exception.PharmaException;
import com.ust.pharma.model.Batch;
import com.ust.pharma.service.IPharmaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PharmaControllerTest {

    @Mock
    private IPharmaService pharmaService;

    @InjectMocks
    private PharmaController pharmaController;

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
        when(pharmaService.addBatch(any(BatchVO.class))).thenReturn(true);
        ResponseEntity<String> responseEntity = pharmaController.addBatch(batchVO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Batch SuccessFully Added in the System", responseEntity.getBody());
    }

    @Test
    public void testAddBatch_Failure() throws PharmaException, PharmaBusinessException {
        BatchVO batchVO = new BatchVO();
        batchVO.setBatchCode("BATCH001");
        batchVO.setWeight(BigDecimal.valueOf(100));
        batchVO.setPrice(BigDecimal.valueOf(50));
        batchVO.setMedicineCode("MED001");
        batchVO.setMedicineTypeCode("C");
        when(pharmaService.addBatch(any(BatchVO.class))).thenReturn(false);
        ResponseEntity<String> responseEntity = pharmaController.addBatch(batchVO);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testAddBatch_PharmaException() throws PharmaException, PharmaBusinessException {
        BatchVO batchVO = new BatchVO();
        batchVO.setBatchCode("BATCH001");
        batchVO.setWeight(BigDecimal.valueOf(100));
        batchVO.setPrice(BigDecimal.valueOf(50));
        batchVO.setMedicineCode("MED001");
        batchVO.setMedicineTypeCode("C");

        when(pharmaService.addBatch(any(BatchVO.class))).thenThrow(new PharmaException("Test PharmaException"));
        ResponseEntity<String> responseEntity = pharmaController.addBatch(batchVO);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Test PharmaException", responseEntity.getBody());
    }

    @Test
    public void testAddBatch_PharmaBusinessException() throws PharmaException, PharmaBusinessException {
        BatchVO batchVO = new BatchVO();
        batchVO.setBatchCode("BATCH001");
        batchVO.setWeight(BigDecimal.valueOf(100));
        batchVO.setPrice(BigDecimal.valueOf(50));
        batchVO.setMedicineCode("MED001");
        batchVO.setMedicineTypeCode("C");
        when(pharmaService.addBatch(any(BatchVO.class))).thenThrow(new PharmaBusinessException("Test PharmaBusinessException",404));
        ResponseEntity<String> responseEntity = pharmaController.addBatch(batchVO);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Test PharmaBusinessException", responseEntity.getBody());
    }

    @Test
    public void testFetchAllBatch_Success() {
        int page = 0;
        int size = 10;
        List<Batch> batchList = new ArrayList<>();
        batchList.add(new Batch());
        Page<Batch> batchPage = new PageImpl<>(batchList);
        when(pharmaService.fetchAllBatch(any(Pageable.class))).thenReturn(batchPage);
        ResponseEntity<Page <Batch>> responseEntity = pharmaController.fetchAllBatch(page, size);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(batchPage, responseEntity.getBody());
        verify(pharmaService, times(1)).fetchAllBatch(any(Pageable.class));
    }

    @Test
    public void testFetchBatchById_BatchExists() throws PharmaException {
        String batchCode = "BTC-1234";
        Batch expectedBatch = new Batch();
        expectedBatch.setBatchCode(batchCode);
        when(pharmaService.fetchBatchById(batchCode)).thenReturn(expectedBatch);
        ResponseEntity<Batch> responseEntity = pharmaController.fetchBatchById(batchCode);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedBatch, responseEntity.getBody());
    }

    @Test
    public void testFetchBatchById_BatchDoesNotExist() throws PharmaException {
        String batchCode = "BTC-5678";
        when(pharmaService.fetchBatchById(batchCode)).thenReturn(null);
        ResponseEntity<Batch> responseEntity = pharmaController.fetchBatchById(batchCode);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
