package com.panin.testtask.weatherservice.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceNotSupportedException extends WeatherServiceException {

    public ServiceNotSupportedException() {
        JSONMessage ="{\"cod\":\"400\",\"message\":\"weather service is not supported\"}";
        httpStatus= HttpStatus.BAD_REQUEST;
    }
}
