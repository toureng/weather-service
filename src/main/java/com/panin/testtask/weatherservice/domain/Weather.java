package com.panin.testtask.weatherservice.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;


    private String service;

    @JsonProperty(value = "requested_city")
    private String city;

    private Date date;


    @JsonProperty(value = "received_location")
    private String receivedLocation;

    private String description;

    private String longitude;

    private String latitude;


    private String temperature;

    @JsonProperty(value = "feels_like")
    private String feelsLike;

    @JsonProperty(value = "temperature_min")
    private String temperatureMin;

    @JsonProperty(value = "temperature_max")
    private String temperatureMax;


    @JsonProperty(value = "wind_speed")
    private String windSpeed;

    @JsonProperty(value = "wind_deg")
    private String windDeg;


    private String humidity;

    private String pressure;

    private String clouds;

    @JsonProperty(value = "dew_point")
    private String dewPoint;

    private String visibility;


    private String precipitation;

    private String rain;

    private String snow;


    public Weather() {
    }

    public Weather(String service, String city) {
        this.service = service;
        this.city = city;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return "Weather{" +
                "service='" + service + '\'' +
                ", city='" + city + '\'' +
                ", date=" + date +
                ", receivedLocation='" + receivedLocation + '\'' +
                ", description='" + description + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", temperature='" + temperature + '\'' +
                ", feelsLike='" + feelsLike + '\'' +
                ", temperatureMin='" + temperatureMin + '\'' +
                ", temperatureMax='" + temperatureMax + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windDeg='" + windDeg + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", clouds='" + clouds + '\'' +
                ", dewPoint='" + dewPoint + '\'' +
                ", visibility='" + visibility + '\'' +
                ", precipitation='" + precipitation + '\'' +
                ", rain='" + rain + '\'' +
                ", snow='" + snow + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(service, weather.service) &&
                Objects.equals(city, weather.city) &&
                Objects.equals(receivedLocation, weather.receivedLocation) &&
                Objects.equals(description, weather.description) &&
                Objects.equals(longitude, weather.longitude) &&
                Objects.equals(latitude, weather.latitude) &&
                Objects.equals(temperature, weather.temperature) &&
                Objects.equals(feelsLike, weather.feelsLike) &&
                Objects.equals(temperatureMin, weather.temperatureMin) &&
                Objects.equals(temperatureMax, weather.temperatureMax) &&
                Objects.equals(windSpeed, weather.windSpeed) &&
                Objects.equals(windDeg, weather.windDeg) &&
                Objects.equals(humidity, weather.humidity) &&
                Objects.equals(pressure, weather.pressure) &&
                Objects.equals(clouds, weather.clouds) &&
                Objects.equals(dewPoint, weather.dewPoint) &&
                Objects.equals(visibility, weather.visibility) &&
                Objects.equals(precipitation, weather.precipitation) &&
                Objects.equals(rain, weather.rain) &&
                Objects.equals(snow, weather.snow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, city, receivedLocation, description, longitude, latitude,
                temperature, feelsLike, temperatureMin, temperatureMax, windSpeed, windDeg, humidity,
                pressure, clouds, dewPoint, visibility, precipitation, rain, snow);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReceivedLocation() {
        return receivedLocation;
    }

    public void setReceivedLocation(String receivedLocation) {
        this.receivedLocation = receivedLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(String temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public String getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(String temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(String windDeg) {
        this.windDeg = windDeg;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(String dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getSnow() {
        return snow;
    }

    public void setSnow(String snow) {
        this.snow = snow;
    }
}
