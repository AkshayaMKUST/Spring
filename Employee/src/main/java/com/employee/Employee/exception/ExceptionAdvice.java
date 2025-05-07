package com.employee.Employee.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handle(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append("\n" + violation.getMessage()));
            errorMessage = builder.toString();
        } else {
            errorMessage = "ConstraintViolationException occured.";
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EmployeeNotFound.class)
    public ResponseEntity<String> handleException(EmployeeNotFound employeeNotFound){
        return new ResponseEntity<>(employeeNotFound.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EmployeeAlreadyExists.class)
    public ResponseEntity<String> handleException(EmployeeAlreadyExists employeeAlreadyExists){
        return new ResponseEntity<>(employeeAlreadyExists.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EmployeeValidationException.class)
    public ResponseEntity<String> handleException(EmployeeValidationException employeeValidationException){
        return new ResponseEntity<>(employeeValidationException.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
