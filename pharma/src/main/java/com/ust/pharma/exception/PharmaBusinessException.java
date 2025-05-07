package com.ust.pharma.exception;

public class PharmaBusinessException extends Exception{
    private int errorCode;

    public PharmaBusinessException(String message) {
        super(message);
    }

    public PharmaBusinessException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
