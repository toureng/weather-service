package com.panin.testtask.weatherservice.weatherproviders;

import com.panin.testtask.weatherservice.domain.Weather;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;

import static org.mockito.ArgumentMatchers.any;

public class WorldWeatherOnlineProviderTest {
    private final String example = "{\n" +
            "  \"data\": {\n" +
            "    \"request\": [\n" +
            "      {\n" +
            "        \"type\": \"City\",\n" +
            "        \"query\": \"test\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"current_condition\": [\n" +
            "      {\n" +
            "        \"temp_C\": \"0\",\n" +
            "        \"temp_F\": \"0\",\n" +
            "        \"weatherCode\": \"0\",\n" +
            "        \n" +
            "        \"weatherDesc\": [\n" +
            "          {\n" +
            "            \"value\": \"clear\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"windspeedMiles\": \"0\",\n" +
            "        \"windspeedKmph\": \"0\",\n" +
            "        \"winddirDegree\": \"0\",\n" +
            "        \"precipMM\": \"0\",\n" +
            "        \"precipInches\": \"0\",\n" +
            "        \"humidity\": \"0\",\n" +
            "        \"visibility\": \"0\",\n" +
            "        \"visibilityMiles\": \"0\",\n" +
            "        \"pressure\": \"0\",\n" +
            "        \"pressureInches\": \"0\",\n" +
            "        \"cloudcover\": \"0\",\n" +
            "        \"FeelsLikeC\": \"0\",\n" +
            "        \"FeelsLikeF\": \"0\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    @Test
    public void test() {
        RequestExecutor requestExecutor = Mockito.mock(RequestExecutor.class);
        Mockito.when(requestExecutor.executeRequest(any()))
                .thenReturn(new ResponseEntity<String>(example, null, HttpStatus.OK));

        WeatherProvider provider = new WorldWeatherOnlineProvider("worldweatheronline",
                "key",
                requestExecutor);

        Weather weather = new Weather("worldweatheronline", "test");
        weather.setReceivedLocation("test");
        weather.setTemperature("0");
        weather.setDescription("clear");
        weather.setWindSpeed("0.00");
        weather.setWindDeg("0");
        weather.setPrecipitation("0");
        weather.setHumidity("0");
        weather.setVisibility("0");
        weather.setPressure("0");
        weather.setClouds("0");
        weather.setFeelsLike("0");

        Assert.assertEquals(weather, provider.getWeather("test"));
    }

    @Test(expected = RestClientResponseException.class)
    public void badResponse() {
        RequestExecutor requestExecutor = Mockito.mock(RequestExecutor.class);
        Mockito.when(requestExecutor.executeRequest(any()))
                .thenReturn(new ResponseEntity<String>(example, null, HttpStatus.NOT_FOUND));

        WeatherProvider provider = new WorldWeatherOnlineProvider("worldweatheronline",
                "key",
                requestExecutor);

        provider.getWeather("test");

    }
}