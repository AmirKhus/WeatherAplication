package ru.practicum.strategy;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.util.Map;

import static ru.practicum.util.Util.*;

public class CityNameWeatherInput implements WeatherInputStrategy {
    private final String weatherApiKey = propertyReader.getProperty("api.weatherApiKey");

    @Override
    public String getWeather(Map<String, String> cityName) {
        StringBuilder urlGetWeatherByName =
                new StringBuilder("http://api.openweathermap.org/geo/1.0/direct?q={NAME_CTY}&limit=5&appid={API_KEY}");

        String name = cityName.get("cityName");

        replacePlaceholder(urlGetWeatherByName, "{NAME_CTY}", name);
        replacePlaceholder(urlGetWeatherByName, "{API_KEY}", weatherApiKey);

        InputStreamReader reader = getInputStreamReader(urlGetWeatherByName.toString());
        JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

        Map<String, String> coordinates = validationLongitudeAndLatitude(jsonArray);

        if (coordinates != null) {
            WeatherInputStrategy weatherInputStrategy = new CoordinatesWeatherInput();
            coordinates.put("cityName", name);
            return weatherInputStrategy.getWeather(coordinates);
        } else {
            return "Specified city " + name + " not found";
        }
    }
}
