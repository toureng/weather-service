package com.panin.testtask.weatherservice.weatherproviders;

import com.fasterxml.jackson.databind.JsonNode;
import com.panin.testtask.weatherservice.domain.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class OpenWeatherMapProvider extends WeatherProvider {
    private String urlTemplate = "http://api.openweathermap.org/data/2.5/weather?" +
            "q=%s" +
            "&appid=%s" +
            "&units=metric";

    private String key;

    @Autowired
    public OpenWeatherMapProvider(@Value("openweathermap") String weatherProviderName,
                                  @Value("${app.openweathermapkey}") String key,
                                  RequestExecutor requestExecutor) {

        super(weatherProviderName, requestExecutor);
        this.key = key;
    }

    @Override
    protected String getUrl(String city) {
        return String.format(urlTemplate, city, key);
    }

    @Override
    void fillWeather(JsonNode root, Weather weather) {

        JsonNode coord = root.path("coord");
        weather.setLongitude(value(coord.get("lon")));
        weather.setLatitude(value(coord.get("lat")));

        JsonNode main = root.path("main");
        weather.setTemperature(value(main.get("temp")));
        weather.setFeelsLike(value(main.get("feels_like")));
        weather.setTemperatureMin(value(main.get("temp_min")));
        weather.setTemperatureMax(value(main.get("temp_max")));
        weather.setHumidity(value(main.get("humidity")));
        weather.setPressure(value(main.get("pressure")));

        JsonNode wind = root.path("wind");
        weather.setWindSpeed(value(wind.get("speed")));
        weather.setWindDeg(value(wind.get("deg")));

        weather.setDescription(value(root.path("weather").path(0).get("description")));
        weather.setReceivedLocation(value(root.get("name")));
        weather.setClouds(value(root.path("clouds").get("all")));
        weather.setRain(value(root.path("rain").get("1h")));
        weather.setSnow(value(root.path("snow").get("1h")));

        JsonNode visibilityM = root.get("visibility");
        if (visibilityM != null) {
            double visibilityKm = visibilityM.asDouble() / 1000;
            weather.setVisibility(String.format(Locale.ENGLISH, "%.2f", visibilityKm));
        }

    }


}
