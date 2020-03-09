package com.panin.testtask.weatherservice.errors.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ServiceException extends RuntimeException {
    protected HttpStatus code;

    public ServiceException(String message) {
        super(message);
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }
}
