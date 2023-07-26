package com.maidev.loan.service;

public class IbanInvalidException extends RuntimeException{
    public IbanInvalidException(String message) {
        super(message);
    }    
}
