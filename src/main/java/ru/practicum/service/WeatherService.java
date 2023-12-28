package ru.practicum.service;

import ru.practicum.strategy.WeatherInputStrategy;

import java.util.Map;

public class WeatherService {
    private WeatherInputStrategy inputStrategy;

    public WeatherService(WeatherInputStrategy inputStrategy) {
        this.inputStrategy = inputStrategy;
    }

    public WeatherService() {
    }

    public void setInputStrategy(WeatherInputStrategy inputStrategy) {
        this.inputStrategy = inputStrategy;
    }

    public String getWeather(Map<String, String> informationAboutCity) {
        return inputStrategy.getWeather(informationAboutCity);
    }


    public String getWeather() {
        return inputStrategy.getWeather(null);
    }
}