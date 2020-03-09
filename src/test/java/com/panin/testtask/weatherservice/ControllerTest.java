package com.panin.testtask.weatherservice;

import com.panin.testtask.weatherservice.domain.Weather;
import com.panin.testtask.weatherservice.errors.exceptions.BadRequestException;
import com.panin.testtask.weatherservice.errors.exceptions.ServiceNotSupportedException;
import com.panin.testtask.weatherservice.repos.WeatherRepo;
import com.panin.testtask.weatherservice.weatherproviders.ProviderManager;
import com.panin.testtask.weatherservice.weatherproviders.WeatherProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;


public class ControllerTest {
    private ProviderManager manager;
    private WeatherRepo repo;
    private Weather weatherFromService;
    private Weather weatherFromDB;
    private Weather weatherFromDBOld;
    private WeatherProvider weatherProvider;
    private Controller controller;
    private int threshold = 100;

    @Before
    public void before() {
        weatherProvider = Mockito.mock(WeatherProvider.class);
        manager = Mockito.mock(ProviderManager.class);
        repo = Mockito.mock(WeatherRepo.class);
        weatherFromService = new Weather("service", "city");
        weatherFromDB = new Weather("service", "from_db");
        weatherFromDBOld = new Weather("service", "from_db_old");


        Mockito.when(manager.getWeatherProvider("service")).thenReturn(weatherProvider);

        Mockito.when(weatherProvider.getWeather(any())).thenReturn(weatherFromService);

        Mockito.when(repo.findByCityAndService("from_db", "service"))
                .thenReturn(Collections.singletonList(weatherFromDB));

        Mockito.when(repo.findByCityAndService("from_db_old", "service"))
                .thenReturn(Collections.singletonList(weatherFromDBOld));

        controller = new Controller(repo, manager, threshold);
    }


    @Test(expected = BadRequestException.class)
    public void nullCity() {
        controller.get("some", null);
    }

    @Test(expected = BadRequestException.class)
    public void emptyCity() {
        controller.get("some", "");
    }

    @Test(expected = BadRequestException.class)
    public void nullService() {
        controller.get(null, "some");
    }

    @Test(expected = BadRequestException.class)
    public void emptyService() {
        controller.get("", "some");
    }

    @Test(expected = ServiceNotSupportedException.class)
    public void notSupportedService() {
        controller.get("notSupportedService", "some");
    }

    @Test
    public void fromService() {
        weatherFromService.setDate(new Date());

        Assert.assertEquals(weatherFromService, controller.get("service", "city"));
        Mockito.verify(repo).findByCityAndService(any(), any());
        Mockito.verify(repo).save(any());
    }

    @Test
    public void fromDB() {
        weatherFromDB.setDate(new Date());

        Assert.assertEquals(weatherFromDB, controller.get("service", "from_db"));
        Mockito.verify(repo).findByCityAndService(any(), any());
    }

    @Test
    public void ifOld() {
        Date old = new Date(new Date().getTime() - threshold * 1000 - 1);
        weatherFromDBOld.setDate(old);

        Assert.assertEquals(weatherFromService, controller.get("service", "from_db_old"));
        Mockito.verify(repo).findByCityAndService(any(), any());
        Mockito.verify(repo).delete(any());
        Mockito.verify(repo).save(any());
    }


}