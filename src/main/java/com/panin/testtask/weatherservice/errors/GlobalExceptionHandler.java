package com.panin.testtask.weatherservice.errors;

import com.panin.testtask.weatherservice.errors.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorMessage> noHandler(NoHandlerFoundException ex) {
        LOGGER.debug("Bad request: '{}' not found.", ex.getRequestURL());

        ErrorMessage em = new ErrorMessage(String.format("%s not found", ex.getRequestURL()),
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorMessage>(em, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorMessage> internal(ServiceException ex) {
        LOGGER.debug("Bad request: '{}'", ex.getMessage());

        ErrorMessage em = new ErrorMessage(ex.getMessage(), ex.getCode().value());
        return new ResponseEntity<ErrorMessage>(em, HttpStatus.valueOf(ex.getCode().value()));
    }

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ErrorMessage> external(RestClientResponseException ex) {
        LOGGER.debug("Weather service error. Code: '{}', response: '{}'.", ex.getRawStatusCode(),
                ex.getResponseBodyAsString());

        ErrorMessage em = new ErrorMessage("Weather service error.",
                ex.getRawStatusCode(), ex.getResponseBodyAsString());

        return new ResponseEntity<ErrorMessage>(em, HttpStatus.valueOf(ex.getRawStatusCode()));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> error(Exception ex) {
        LOGGER.error("Internal error.", ex);

        ErrorMessage em = new ErrorMessage("Internal error.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<ErrorMessage>(em, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
