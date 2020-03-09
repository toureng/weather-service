package com.panin.testtask.weatherservice.weatherproviders;

import com.fasterxml.jackson.databind.JsonNode;
import com.panin.testtask.weatherservice.domain.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherBitProvider extends WeatherProvider {
    private String url = "http://api.weatherbit.io/v2.0/current?" +
            "&city=%s" +
            "&key=%s";

    private String key;

    @Autowired
    public WeatherBitProvider(@Value("weatherbit") String weatherProviderName,
                              @Value("${app.weatherbitkey}") String key,
                              RequestExecutor requestExecutor) {

        super(weatherProviderName, requestExecutor);
        this.key = key;
    }

    @Override
    String getUrl(String city) {
        return String.format(url, city, key);
    }

    @Override
    void fillWeather(JsonNode root, Weather weather) {

        JsonNode data = root.path("data").path(0);
        weather.setLongitude(value(data.get("lon")));
        weather.setLatitude(value(data.get("lat")));
        weather.setDescription(value(data.path("weather").get("description")));
        weather.setReceivedLocation(value(data.get("city_name")));
        weather.setTemperature(value(data.get("temp")));
        weather.setFeelsLike(value(data.get("app_temp")));
        weather.setHumidity(value(data.get("rh")));
        weather.setPressure(value(data.get("pres")));
        weather.setWindSpeed(value(data.get("wind_spd")));
        weather.setWindDeg(value(data.get("wind_dir")));
        weather.setClouds(value(data.get("clouds")));
        weather.setPrecipitation(value(data.get("precip")));
        weather.setSnow(value(data.get("snow")));
        weather.setDewPoint(value(data.get("dewpt")));
        weather.setVisibility(value(data.get("vis")));

    }
}
