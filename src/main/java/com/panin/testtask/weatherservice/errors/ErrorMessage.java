package com.panin.testtask.weatherservice.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {
    private String message;
    private int code;
    private Instant timestamp;

    @JsonRawValue
    private String details;


    public ErrorMessage(String message, int code) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public ErrorMessage(String message, int code, String details) {
        this(message, code);
        this.details = details;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
