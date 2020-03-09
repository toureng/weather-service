package com.panin.testtask.weatherservice.weatherproviders;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;


public class ProviderManagerTest {
    private ProviderManager manager;
    private WeatherProvider provider;

    @Before
    public void before() {
        provider = Mockito.mock(WeatherProvider.class);
        Mockito.when(provider.getWeatherProviderName()).thenReturn("test");
        manager = new ProviderManager(Collections.singletonList(provider));
    }

    @Test
    public void getWeatherProvider() {
        Assert.assertEquals(provider, manager.getWeatherProvider("test"));
        Assert.assertNull(manager.getWeatherProvider("notSupportedProvider"));

    }
}