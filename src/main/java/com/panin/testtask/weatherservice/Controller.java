package com.panin.testtask.weatherservice;

import com.panin.testtask.weatherservice.domain.Weather;
import com.panin.testtask.weatherservice.errors.exceptions.BadRequestException;
import com.panin.testtask.weatherservice.errors.exceptions.ServiceNotSupportedException;
import com.panin.testtask.weatherservice.repos.WeatherRepo;
import com.panin.testtask.weatherservice.weatherproviders.ProviderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@RestController
public class Controller {
    private WeatherRepo weatherRepo;
    private ProviderManager providerManager;
    private int thresholdSeconds;

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @Autowired
    public Controller(WeatherRepo weatherRepo,
                      ProviderManager providerManager,
                      @Value("${app.thresholdseconds}") int thresholdSeconds) {

        this.weatherRepo = weatherRepo;
        this.providerManager = providerManager;
        this.thresholdSeconds = thresholdSeconds;
    }

    @GetMapping(produces = {"application/JSON"})
    public Weather get(@RequestParam(required = false) String service,
                       @RequestParam(required = false) String city) {

        LOGGER.debug("Start of request processing: city='{}', service='{}'.", city, service);

        if (service == null || service.isEmpty()) {
            throw new BadRequestException("The service is not set");
        }

        if (city == null || city.isEmpty()) {
            throw new BadRequestException("The city is not set");
        }

        service = service.toLowerCase();
        city = city.toLowerCase();

        if (providerManager.getWeatherProvider(service) == null)
            throw new ServiceNotSupportedException("The service '" + service + "' is not supported.");

        Weather weather = getWeatherFromCache(service, city);
        if (weather == null)
            weather = getAndSaveWeather(service, city);
        else if (timeIsUp(weather))
            weather = getAndUpdateWeather(weather);

        LOGGER.debug("The request has been processed. Result: {}", weather);

        return weather;
    }


    private Weather getWeatherFromCache(String service, String city) {
        LOGGER.debug("Getting data from the database.");

        List<Weather> weatherList = weatherRepo.findByCityAndService(city, service);
        Weather weather = null;
        if (!weatherList.isEmpty())
            weather = weatherList.get(0);
        return weather;
    }

    private Weather getAndUpdateWeather(Weather weather) {
        LOGGER.debug("Deletion data from the database.");

        weatherRepo.delete(weather);
        return getAndSaveWeather(weather.getService(), weather.getCity());
    }

    private Weather getAndSaveWeather(String service, String city) {
        Weather weather = providerManager.getWeatherProvider(service).getWeather(city);
        if (weather != null) {
            LOGGER.debug("Saving data to the database.");
            weatherRepo.save(weather);
        }
        return weather;
    }

    private boolean timeIsUp(Weather weather) {
        Date weatherDate = weather.getDate();
        Date threshold = new Date(new Date().getTime() - thresholdSeconds * 1000);
        return weatherDate.before(threshold);
    }


}
