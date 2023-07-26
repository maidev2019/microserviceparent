package com.maidev.ibanservice.exception;

public class InvalidCountryException extends RuntimeException {
    public InvalidCountryException(String message) {
        super(message);
    }    
}
