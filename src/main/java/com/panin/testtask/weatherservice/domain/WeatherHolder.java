package com.panin.testtask.weatherservice.domain;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class WeatherHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 8192)
    private String responseHolder;

    private LocalDateTime date;
    private String service;
    private String city;




    public WeatherHolder() {
    }

    public WeatherHolder(String service, String city, String responseHolder, LocalDateTime date) {
        this.service = service;
        this.city = city;
        this.responseHolder = responseHolder;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getResponseHolder() {
        return responseHolder;
    }

    public void setResponseHolder(String responseHolder) {
        this.responseHolder = responseHolder;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
