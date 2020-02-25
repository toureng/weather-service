package com.panin.testtask.weatherservice.weatherproviders;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WorldWeatherOnlineProvider extends WeatherProvider {
    private String url = "http://api.worldweatheronline.com/premium/v1/weather.ashx?" +
            "q=%s" +
            "&key=%s" +
            "&num_of_days=1" +
            "&format=json" +
            "&fx=no" +
            "&mca=no";

    private String key;

    public WorldWeatherOnlineProvider(@Value("worldweatheronline") String weatherProviderName,
                                      @Value("${app.worldweatheronlinekey}") String key) {
        super(weatherProviderName);
        this.key = key;
    }

    @Override
    String getUrl(String city) {
        return String.format(url, city, key);
    }
}
