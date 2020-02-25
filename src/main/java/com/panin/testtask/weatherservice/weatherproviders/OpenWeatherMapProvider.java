package com.panin.testtask.weatherservice.weatherproviders;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenWeatherMapProvider extends WeatherProvider {
    private String urlTemplate = "http://api.openweathermap.org/data/2.5/weather?" +
            "q=%s" +
            "&appid=%s" +
            "&units=metric";

    private String key;

    public OpenWeatherMapProvider(@Value("openweathermap") String weatherProviderName,
                                  @Value("${app.openweathermapkey}") String key) {
        super(weatherProviderName);
        this.key = key;
    }

    @Override
    protected String getUrl(String city) {
        return String.format(urlTemplate, city, key);
    }
}
