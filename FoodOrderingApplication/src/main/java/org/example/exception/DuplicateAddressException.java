package org.example.exception;

public class DuplicateAddressException extends Exception{
    public DuplicateAddressException(String message){
        super(message);
    }
}
