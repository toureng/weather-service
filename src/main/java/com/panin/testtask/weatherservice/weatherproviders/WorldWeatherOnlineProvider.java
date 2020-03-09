package com.panin.testtask.weatherservice.weatherproviders;

import com.fasterxml.jackson.databind.JsonNode;
import com.panin.testtask.weatherservice.domain.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

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

    @Autowired
    public WorldWeatherOnlineProvider(@Value("worldweatheronline") String weatherProviderName,
                                      @Value("${app.worldweatheronlinekey}") String key,
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

        weather.setReceivedLocation(value(root.path("data").path("request").path(0).get("query")));

        JsonNode current = root.path("data").path("current_condition").path(0);
        weather.setDescription(value(current.path("weatherDesc").path(0).get("value")));
        weather.setTemperature(value(current.get("temp_C")));
        weather.setFeelsLike(value(current.get("FeelsLikeC")));
        weather.setHumidity(value(current.get("humidity")));
        weather.setPressure(value(current.get("pressure")));
        weather.setWindDeg(value(current.get("winddirDegree")));
        weather.setClouds(value(current.get("cloudcover")));
        weather.setPrecipitation(value(current.get("precipMM")));
        weather.setVisibility(value(current.get("visibility")));

        JsonNode windSpeedKmph = current.get("windspeedKmph");
        if (windSpeedKmph != null) {
            double windSpeedMps = windSpeedKmph.asDouble() * 1000 / 3600;
            weather.setWindSpeed(String.format(Locale.ENGLISH, "%.2f", windSpeedMps));
        }

    }
}
