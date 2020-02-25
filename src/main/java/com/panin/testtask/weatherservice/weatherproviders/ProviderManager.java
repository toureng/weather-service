package com.panin.testtask.weatherservice.weatherproviders;

import com.panin.testtask.weatherservice.weatherproviders.WeatherProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProviderManager {
    private Map<String, WeatherProvider> weatherProviders;

    @Autowired
    public ProviderManager(List<WeatherProvider> providerList) {
        weatherProviders=new HashMap<>();
        for (WeatherProvider provider : providerList) {
            weatherProviders.put(provider.getWeatherProviderName(),provider);
        }
    }

    public WeatherProvider getWeatherProvider(String service){
        return weatherProviders.get(service);
    }
}
