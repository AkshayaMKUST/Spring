package com.ust.pharma.controller;


import com.ust.pharma.constants.CommonConstants;
import com.ust.pharma.dto.BatchVO;
import com.ust.pharma.exception.PharmaBusinessException;
import com.ust.pharma.exception.PharmaException;
import com.ust.pharma.model.Batch;
import com.ust.pharma.service.IPharmaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Implement Rest Api to save new batch
 */
@RestController
@RequestMapping
public class PharmaController {
    private Logger logger = LoggerFactory.getLogger(PharmaController.class);

    @Autowired
    IPharmaService iPharmaService;

    /**
     * Add a new batch
     * @param batchVO
     * @return ResponseEntity<String>
     */
    @PostMapping
    public ResponseEntity<String> addBatch(@RequestBody BatchVO batchVO) {
        logger.info("Invoked controller method addBatch" + batchVO);
        ResponseEntity<String> responseEntity;
        try {
            boolean response = iPharmaService.addBatch(batchVO);
            if (response) {
                responseEntity = ResponseEntity.ok(CommonConstants.ADDED_SUCCESSFULLY);
            } else {
                responseEntity = ResponseEntity.badRequest().build();
            }
        } catch (PharmaBusinessException e) {
            responseEntity = ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        } catch (PharmaException e) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return responseEntity;
    }

    /**
     * Fetch all batch
     * @return ResponseEntity<List>
     */
    @GetMapping("/all")
    public ResponseEntity<Page<Batch>> fetchAllBatch(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Invoked controller method fetchAllBatch");

            Pageable pageable = PageRequest.of(page, size);
            Page<Batch> batchPage = iPharmaService.fetchAllBatch(pageable);
            return ResponseEntity.ok(batchPage);

    }

    /**
     * Fetch batch by batch code
     * @param batchCode
     * @return ResponseEntity<Batch>
     */
    @GetMapping
    public ResponseEntity<Batch> fetchBatchById(@RequestParam String batchCode) throws PharmaException {
        logger.info("Invoked controller method fetchBatchById for batchCode: " + batchCode);
        ResponseEntity<Batch> responseEntity;
            Batch batch = iPharmaService.fetchBatchById(batchCode);
            if (batch != null) {
                responseEntity = ResponseEntity.ok(batch);
            } else {
                responseEntity = ResponseEntity.notFound().build();
            }
        return responseEntity;
    }


}
