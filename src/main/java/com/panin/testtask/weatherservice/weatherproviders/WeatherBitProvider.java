package com.panin.testtask.weatherservice.weatherproviders;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherBitProvider extends WeatherProvider {
    private String url = "http://api.weatherbit.io/v2.0/current?" +
            "&city=%s" +
            "&key=%s";
    private String key;

    public WeatherBitProvider(@Value("weatherbit") String weatherProviderName,
                              @Value("${app.weatherbitkey}") String key) {
        super(weatherProviderName);
        this.key = key;
    }

    @Override
    String getUrl(String city) {
        return String.format(url, city, key);
    }


}
