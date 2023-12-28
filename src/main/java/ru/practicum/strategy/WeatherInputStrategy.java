package ru.practicum.strategy;

import java.util.Map;

public interface WeatherInputStrategy {
    String getWeather(Map<String, String> input);

}
