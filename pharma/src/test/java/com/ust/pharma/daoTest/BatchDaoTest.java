package com.ust.pharma.daoTest;

import com.ust.pharma.daoRepository.BatchDAO;
import com.ust.pharma.exception.PharmaBusinessException;
import com.ust.pharma.exception.PharmaException;
import com.ust.pharma.model.Batch;
import com.ust.pharma.model.MedicineMaster;
import com.ust.pharma.model.ShippingMaster;
import com.ust.pharma.repository.IBatchRepository;
import com.ust.pharma.repository.IMedicineMasterRepository;
import com.ust.pharma.repository.IShippingMasterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BatchDaoTest {
    @Mock
    IBatchRepository iBatchRepository;

    @Mock
    IShippingMasterRepository iShippingMasterRepository;

    @Mock
    IMedicineMasterRepository iMedicineMasterRepository;



    @InjectMocks
    BatchDAO batchDAO;

    @Test
    public void testAddBatch() throws PharmaException {
        Batch batch = new Batch();
        when(iBatchRepository.save(Mockito.any())).thenReturn(batch);
        assertTrue(batchDAO.addDetails(batch));
        verify(iBatchRepository, times(1)).save(batch);
    }

    @Test
    public void testAddDetails_Failure() throws PharmaException {
        Batch batch = new Batch();
        batch.setBatchCode("BTC-1234");
        batch.setMedicineCode("MC_301");
        batch.setWeight(BigDecimal.valueOf(150));

        when(iBatchRepository.save(batch)).thenThrow(new RuntimeException());

        assertThrows(PharmaException.class,()->{
            batchDAO.addDetails(batch);
        });
        verify(iBatchRepository, times(1)).save(batch);
    }

    @Test
    public void testGetShippingCharge_NotNull() throws PharmaBusinessException {
        ShippingMaster shippingMaster=new ShippingMaster();
        shippingMaster.setShippingCharge(BigDecimal.valueOf(50.50));
        when(iShippingMasterRepository.findByMedicineTypeCodeAndWeightRange(Mockito.anyString(),Mockito.anyString())).thenReturn(shippingMaster);
        batchDAO.getShippingCharge("C","W2");
    }

    @Test
    public void testGetShippingCharge_Null(){
        when(iShippingMasterRepository.findByMedicineTypeCodeAndWeightRange(Mockito.anyString(),Mockito.anyString())).thenReturn(null);
        assertThrows(PharmaBusinessException.class,()->{
            batchDAO.getShippingCharge("C","W2");
        });
    }

    @Test
    public void testCheckMedicineCode_NotExist(){
        MedicineMaster medicineMaster = new MedicineMaster();
        String medicineCode = "MC_301";
        medicineMaster.setMedicineCode(medicineCode);
        Optional<MedicineMaster> medicineMasterOptional = Optional.of(medicineMaster);
        when(iMedicineMasterRepository.findById(Mockito.anyString())).thenReturn(medicineMasterOptional);
        batchDAO.checkMedicineCode(medicineCode);
    }

    @Test
    public void testCheckBatchCode(){
        Batch batch = new Batch();
        String batchCode = "BTC_1234";
        batch.setBatchCode(batchCode);
        Optional<Batch> batchOptional = Optional.of(batch);
        when(iBatchRepository.findById(Mockito.anyString())).thenReturn(batchOptional);
        batchDAO.checkBatchCode(batchCode);
    }

    @Test
    public void testFetchAllBatch(){
        Pageable pageable = null;
        batchDAO.fetchAllBatch(pageable);
    }

    @Test
    public void testFetchBatchById_Exists() throws PharmaException {
        String batchCode = "BTC-1234";
        Batch batch = new Batch();
        batch.setBatchCode(batchCode);
        when(iBatchRepository.findById(batchCode)).thenReturn(Optional.of(batch));
        Batch resultBatch = batchDAO.fetchBatchById(batchCode);
        assertEquals(batch, resultBatch);
        verify(iBatchRepository, times(1)).findById(batchCode);
    }

    @Test
    public void testFetchBatchById_NotExists() {
        String batchCode = "BTC-1234";
        when(iBatchRepository.findById(batchCode)).thenReturn(Optional.empty());
        assertThrows(PharmaException.class, () -> batchDAO.fetchBatchById(batchCode));
    }
}
