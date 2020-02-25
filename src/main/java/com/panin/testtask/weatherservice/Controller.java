package com.panin.testtask.weatherservice;

import com.panin.testtask.weatherservice.domain.WeatherHolder;
import com.panin.testtask.weatherservice.exceptions.BadRequestException;
import com.panin.testtask.weatherservice.exceptions.ServiceNotSupportedException;
import com.panin.testtask.weatherservice.exceptions.WeatherServiceException;
import com.panin.testtask.weatherservice.repos.WeatherHolderRepo;
import com.panin.testtask.weatherservice.weatherproviders.ProviderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@RestController
public class Controller {
    private WeatherHolderRepo weatherHolderRepo;
    private ProviderManager providerManager;
    private int thresholdSeconds;

    @Autowired
    public Controller(WeatherHolderRepo weatherHolderRepo, ProviderManager providerManager,
                      @Value("${app.thresholdseconds}") int thresholdSeconds) {
        this.weatherHolderRepo = weatherHolderRepo;
        this.providerManager = providerManager;
        this.thresholdSeconds = thresholdSeconds;
    }

    @GetMapping(produces = {"application/JSON"})
    public ResponseEntity<String> getWeather(@RequestParam(required = false) String service,
                                             @RequestParam(required = false) String city) {


        if (service == null || city == null) {
            throw new BadRequestException();
        }

        service = service.toLowerCase();
        city = city.toLowerCase();

        if (providerManager.getWeatherProvider(service) == null)
            throw new ServiceNotSupportedException();

        ResponseEntity<String> response = null;
        WeatherHolder weatherHolder = getWeatherHolderFromCache(service, city);
        if (weatherHolder == null) {
            response = saveAndGetResponse(service, city);
        } else {
            if (timeIsUp(weatherHolder)) {
                response = updateAndGetResponse(weatherHolder);
            } else {
                response = getResponse(weatherHolder);
            }
        }

        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity error(Exception e) {

        if (e instanceof WeatherServiceException) {
            WeatherServiceException ex = (WeatherServiceException) e;
            return ResponseEntity.status(ex.getHttpStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ex.getJSONMessage());
        }

        if (e instanceof HttpStatusCodeException) {
            HttpStatusCodeException ex = (HttpStatusCodeException) e;
            return ResponseEntity.status(ex.getRawStatusCode())
                    .headers(ex.getResponseHeaders())
                    .body(ex.getResponseBodyAsString());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"cod\":\"500\",\"message\":\"internal error\"}");

    }

    private ResponseEntity<String> getResponse(WeatherHolder weatherHolder) {
        return new ResponseEntity<String>(weatherHolder.getResponseHolder(), HttpStatus.OK);
    }

    private WeatherHolder getWeatherHolderFromCache(String service, String city) {
        List<WeatherHolder> weatherHolderList = weatherHolderRepo.findByCityAndService(city, service);
        WeatherHolder weatherHolder = null;
        if (!weatherHolderList.isEmpty())
            weatherHolder = weatherHolderList.get(0);
        return weatherHolder;
    }

    private ResponseEntity<String> updateAndGetResponse(WeatherHolder weatherHolder) {
        weatherHolderRepo.delete(weatherHolder);
        return saveAndGetResponse(weatherHolder.getService(), weatherHolder.getCity());
    }

    private ResponseEntity<String> saveAndGetResponse(String service, String city) {
        ResponseEntity<String> response = providerManager.getWeatherProvider(service).getWeatherResponse(city);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            WeatherHolder weatherHolder = new WeatherHolder(service, city, response.getBody(), LocalDateTime.now());
            weatherHolderRepo.save(weatherHolder);
        }
        return response;
    }

    private boolean timeIsUp(WeatherHolder weatherHolder) {
        LocalDateTime weatherDate = weatherHolder.getDate();
        LocalDateTime threshold = LocalDateTime.now().minus(thresholdSeconds, ChronoUnit.SECONDS);
        return weatherDate.isBefore(threshold);
    }


}
