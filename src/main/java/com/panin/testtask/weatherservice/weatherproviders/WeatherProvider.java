package com.panin.testtask.weatherservice.weatherproviders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public abstract class WeatherProvider {
    private final String weatherProviderName;

    WeatherProvider(String weatherProviderName) {
        this.weatherProviderName = weatherProviderName;
    }

    abstract String getUrl(String city);

    public ResponseEntity<String> getWeatherResponse(String city) {
        return new RestTemplate().getForEntity(getUrl(city), String.class);
    }


    public String getWeatherProviderName() {
        return weatherProviderName;
    }
}
