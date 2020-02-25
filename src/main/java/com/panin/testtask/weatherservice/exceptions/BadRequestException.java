package com.panin.testtask.weatherservice.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends WeatherServiceException {

    public BadRequestException() {
        JSONMessage = "{\"cod\":\"400\",\"message\":\"invalid parameters\"}";
        httpStatus= HttpStatus.BAD_REQUEST;
    }
}
