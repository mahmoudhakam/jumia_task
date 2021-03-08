package com.jumia.phonenumbers.exceptions;

import org.springframework.http.HttpStatus;

public class CustomersNotFound extends BusinessException {
    public CustomersNotFound(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
