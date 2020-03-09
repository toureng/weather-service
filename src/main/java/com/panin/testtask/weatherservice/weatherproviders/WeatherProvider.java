package com.panin.testtask.weatherservice.weatherproviders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panin.testtask.weatherservice.domain.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;


public abstract class WeatherProvider {
    private final String weatherProviderName;
    private RequestExecutor requestExecutor;
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherProvider.class);


    public WeatherProvider(String weatherProviderName, RequestExecutor requestExecutor) {
        this.weatherProviderName = weatherProviderName;
        this.requestExecutor = requestExecutor;
    }

    abstract String getUrl(String city);

    abstract void fillWeather(JsonNode root, Weather weather);

    public Weather getWeather(String city) {
        LOGGER.trace("Getting weather from a weather service is started: service='{}', city='{}'."
                , weatherProviderName, city);

        ResponseEntity<String> response = getWeatherResponse(city);
        JsonNode root = getRoot(response);
        Weather weather = new Weather(getWeatherProviderName(), city);
        fillWeather(root, weather);
        LOGGER.trace("Getting the weather was successful.");
        return weather;
    }


    private ResponseEntity<String> getWeatherResponse(String city) {
        LOGGER.trace("Accessing to '{}'.", weatherProviderName);
        ResponseEntity<String> res = requestExecutor.executeRequest(getUrl(city));

        if (res.getStatusCode().value() != 200) {
            throw new RestClientResponseException("weather service error",
                    res.getStatusCodeValue(),
                    res.getStatusCode().getReasonPhrase(),
                    res.getHeaders(),
                    res.hasBody() ? res.getBody().getBytes() : null,
                    null);
        }
        LOGGER.trace("The response is received.");
        return res;
    }

    private JsonNode getRoot(ResponseEntity<String> response) {
        LOGGER.trace("Parsing the response is started.");
        String responseBody = response.getBody();
        JsonNode root = null;
        try {
            root = new ObjectMapper().readTree(responseBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        LOGGER.trace("Parsing was successful.");
        return root;
    }

    String value(JsonNode node) {
        String result = null;
        if (node != null)
            result = node.asText();

        return result;
    }

    String getWeatherProviderName() {
        return weatherProviderName;
    }
}
