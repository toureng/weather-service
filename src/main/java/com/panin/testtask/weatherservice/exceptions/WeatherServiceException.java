package com.panin.testtask.weatherservice.exceptions;

import org.springframework.http.HttpStatus;

public class WeatherServiceException extends RuntimeException {
    protected HttpStatus httpStatus;
    protected String JSONMessage;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getJSONMessage() {
        return JSONMessage;
    }
}
