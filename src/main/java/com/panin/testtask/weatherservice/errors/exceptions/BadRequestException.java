package com.panin.testtask.weatherservice.errors.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ServiceException {

    public BadRequestException(String message) {
        super(message);
        setCode(HttpStatus.BAD_REQUEST);
    }
}
