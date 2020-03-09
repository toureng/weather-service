package com.panin.testtask.weatherservice.errors.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceNotSupportedException extends ServiceException {

    public ServiceNotSupportedException(String message) {
        super(message);
        setCode(HttpStatus.NOT_FOUND);
    }
}
