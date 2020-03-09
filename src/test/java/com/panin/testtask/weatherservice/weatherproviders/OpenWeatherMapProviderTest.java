package com.panin.testtask.weatherservice.weatherproviders;

import com.panin.testtask.weatherservice.domain.Weather;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;

import static org.mockito.ArgumentMatchers.any;

public class OpenWeatherMapProviderTest {
    private final String example = "{\n" +
            "  \"coord\": {\n" +
            "    \"lon\": 0,\n" +
            "    \"lat\": 0\n" +
            "  },\n" +
            "  \"weather\": [\n" +
            "    {\n" +
            "      \"description\": \"clear\"\n" +
            "    }\n" +
            "  ],\n" +
            "    \"main\": {\n" +
            "    \"temp\": 0,\n" +
            "    \"feels_like\": 0,\n" +
            "    \"temp_min\": 0,\n" +
            "    \"temp_max\": 0,\n" +
            "    \"pressure\": 0,\n" +
            "    \"humidity\": 0\n" +
            "  },\n" +
            "  \"rain\": {\n" +
            "    \"1h\": 0\n" +
            "  },\n" +
            "  \"snow\": {\n" +
            "    \"1h\": 0\n" +
            "  },\n" +
            "  \"visibility\": 0,\n" +
            "  \"wind\": {\n" +
            "    \"speed\": 0,\n" +
            "    \"deg\": 0\n" +
            "  },\n" +
            "  \"clouds\": {\n" +
            "    \"all\": 0\n" +
            "  },\n" +
            "  \"name\": \"test\"\n" +
            "}";

    @Test
    public void test() {
        RequestExecutor requestExecutor = Mockito.mock(RequestExecutor.class);
        Mockito.when(requestExecutor.executeRequest(any()))
                .thenReturn(new ResponseEntity<String>(example, null, HttpStatus.OK));

        WeatherProvider provider = new OpenWeatherMapProvider("openweathermap",
                "key",
                requestExecutor);

        Weather weather = new Weather("openweathermap", "test");
        weather.setLongitude("0");
        weather.setLatitude("0");
        weather.setDescription("clear");
        weather.setTemperature("0");
        weather.setTemperatureMax("0");
        weather.setTemperatureMin("0");
        weather.setFeelsLike("0");
        weather.setHumidity("0");
        weather.setPressure("0");
        weather.setRain("0");
        weather.setSnow("0");
        weather.setVisibility("0.00");
        weather.setWindSpeed("0");
        weather.setWindDeg("0");
        weather.setClouds("0");
        weather.setReceivedLocation("test");

        Assert.assertEquals(weather, provider.getWeather("test"));
    }

    @Test(expected = RestClientResponseException.class)
    public void badResponse() {
        RequestExecutor requestExecutor = Mockito.mock(RequestExecutor.class);
        Mockito.when(requestExecutor.executeRequest(any()))
                .thenReturn(new ResponseEntity<String>(example, null, HttpStatus.NOT_FOUND));

        WeatherProvider provider = new OpenWeatherMapProvider("openweathermap",
                "key",
                requestExecutor);

        provider.getWeather("test");

    }

}