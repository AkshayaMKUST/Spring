package com.ust.pharma.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(PharmaBusinessException.class)
    public ResponseEntity<String> handlePharmaBusinessException(PharmaBusinessException ex){
        logger.error("PharmaBusinessException : "+ex.getMessage());
        return ResponseEntity.status(ex.getErrorCode()).body(ex.getMessage());
    }
    @ExceptionHandler(PharmaException.class)
    public ResponseEntity<String> handlePharmaException(PharmaException ex){
        logger.error("PharmaException : "+ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
