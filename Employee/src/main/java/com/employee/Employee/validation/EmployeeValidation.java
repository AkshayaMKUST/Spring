package com.employee.Employee.validation;

import com.employee.Employee.request.EmployeeRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.employee.Employee.constants.CommonConstants.*;

@Component
public class EmployeeValidation {
    public List<Error> empValidation(EmployeeRequest employeeRequest){
        List<Error> errorList = new ArrayList<>();
        if (ObjectUtils.isEmpty(employeeRequest.getEmployeeId())) {
            errorList.add(createError(EMPLOYEE_ID));
        }
        if (ObjectUtils.isEmpty(employeeRequest.getEmployeeName())) {
            errorList.add(createError(EMPLOYEE_NAME));
        }
        if (ObjectUtils.isEmpty(employeeRequest.getEmployeeAge())) {
            errorList.add(createError(EMPLOYEE_AGE));
        }
        if (ObjectUtils.isEmpty(employeeRequest.getEmployeeDepartment())) {
            errorList.add(createError(EMPLOYEE_DEPARTMENT));
        }
        if (ObjectUtils.isEmpty(employeeRequest.getEmployeeExperience())) {
            errorList.add(createError(EMPLOYEE_EXPERIENCE));
        }
        return errorList;
    }

    public List<Error> fileDataValidation(String[] rowValues) {
        List<Error> errorList = new ArrayList<>();
        if (StringUtils.isEmpty(rowValues[0])) {
            errorList.add(createError(EMPLOYEE_ID));
        }
        if (StringUtils.isEmpty(rowValues[1])) {
            errorList.add(createError(EMPLOYEE_NAME));
        }
        if (StringUtils.isEmpty(rowValues[2])) {
            errorList.add(createError(EMPLOYEE_AGE));
        }
        if (StringUtils.isEmpty(rowValues[3])) {
            errorList.add(createError(EMPLOYEE_DEPARTMENT));
        }
        if (StringUtils.isEmpty(rowValues[4])) {
            errorList.add(createError(EMPLOYEE_EXPERIENCE));
        }
        return errorList;
    }

    private Error createError(String errorMessage) {
        Error error = new Error();
        error.setErrorMessage(errorMessage);
        return error;
    }
}
