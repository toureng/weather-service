package com.panin.testtask.weatherservice.repos;

import com.panin.testtask.weatherservice.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WeatherRepo extends JpaRepository<Weather, Long> {

    List<Weather> findByCityAndService(String city, String service);
}
