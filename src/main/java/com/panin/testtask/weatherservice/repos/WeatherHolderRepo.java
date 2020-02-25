package com.panin.testtask.weatherservice.repos;

import com.panin.testtask.weatherservice.domain.WeatherHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherHolderRepo extends JpaRepository<WeatherHolder, Long> {

    List<WeatherHolder> findByCityAndService(String city, String service);
}
