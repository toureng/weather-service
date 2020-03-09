package com.panin.testtask.weatherservice.weatherproviders;

import com.panin.testtask.weatherservice.domain.Weather;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;

import static org.mockito.ArgumentMatchers.any;

public class WeatherBitProviderTest {
    private final String example = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"rh\": 0,\n" +
            "      \"lon\": 0,\n" +
            "      \"pres\": 0,\n" +
            "      \"clouds\": 0,\n" +
            "      \"city_name\": \"test\",\n" +
            "      \"wind_spd\": 0,\n" +
            "      \"vis\": 0.0,\n" +
            "      \"dewpt\": 0,\n" +
            "      \"snow\": 0,\n" +
            "      \"precip\": 0,\n" +
            "      \"wind_dir\": 0,\n" +
            "      \"lat\": 0,\n" +
            "      \"weather\": {\n" +
            "           \"description\": \"clear\"\n" +
            "      },\n" +
            "      \"temp\": 0,\n" +
            "      \"app_temp\": 0\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void test() {
        RequestExecutor requestExecutor = Mockito.mock(RequestExecutor.class);
        Mockito.when(requestExecutor.executeRequest(any()))
                .thenReturn(new ResponseEntity<String>(example, null, HttpStatus.OK));

        WeatherProvider provider = new WeatherBitProvider("weatherbit",
                "key",
                requestExecutor);

        Weather weather = new Weather("weatherbit", "test");
        weather.setHumidity("0");
        weather.setLongitude("0");
        weather.setPressure("0");
        weather.setClouds("0");
        weather.setReceivedLocation("test");
        weather.setWindSpeed("0");
        weather.setVisibility("0.0");
        weather.setDewPoint("0");
        weather.setSnow("0");
        weather.setPrecipitation("0");
        weather.setWindDeg("0");
        weather.setLatitude("0");
        weather.setDescription("clear");
        weather.setTemperature("0");
        weather.setFeelsLike("0");

        Assert.assertEquals(weather, provider.getWeather("test"));
    }

    @Test(expected = RestClientResponseException.class)
    public void badResponse() {
        RequestExecutor requestExecutor = Mockito.mock(RequestExecutor.class);
        Mockito.when(requestExecutor.executeRequest(any()))
                .thenReturn(new ResponseEntity<String>(example, null, HttpStatus.NOT_FOUND));

        WeatherProvider provider = new WeatherBitProvider("weatherbit",
                "key",
                requestExecutor);

        provider.getWeather("test");
    }
}