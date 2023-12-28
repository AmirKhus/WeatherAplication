package ru.practicum.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.model.InformationAboutWeatherInCity;
import ru.practicum.model.Temperature;
import ru.practicum.model.Weather;
import ru.practicum.model.Wind;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {
    final static String filePath = "src/main/resources/config.properties";
    public final static PropertyReader propertyReader = new PropertyReader(filePath);

    public static InformationAboutWeatherInCity createWeatherInformation(JsonObject jsonObject, String cityName) {
        Wind wind = getWind(jsonObject);
        Weather weather = getWeatherByCityName(jsonObject);
        Temperature temperature = getTemperature(jsonObject);

        return InformationAboutWeatherInCity.builder()
                .wind(wind)
                .weather(weather)
                .nameCity(cityName)
                .temperature(temperature)
                .build();
    }

    public static void replacePlaceholder(StringBuilder stringBuilder, String placeholder, String replacement) {
        int index = stringBuilder.indexOf(placeholder);

        if (index != -1) {
            stringBuilder.replace(index, index + placeholder.length(), replacement);
        }

    }

    public static InputStreamReader getInputStreamReader(String urls) {
        try {
            URL url = new URL(urls);
            return new InputStreamReader(url.openStream());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> validationLongitudeAndLatitude(JsonArray jsonArray) {
        try {
            Map<String, String> coordinates = new HashMap<>();

            coordinates.put("longitude", jsonArray.get(4).getAsJsonObject().get("lon").getAsString());
            coordinates.put("latitude", jsonArray.get(4).getAsJsonObject().get("lat").getAsString());
            return coordinates;
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.toString());
            return null;
        }
    }

    public static Temperature getTemperature(JsonObject jsonObject) {
        Temperature temperature = new Temperature();

        temperature = Temperature.builder()
                .temp(
                        temperature.convertKelvinToCelsius(
                                jsonObject.get("main").getAsJsonObject().get("temp").getAsFloat()
                        ))
                .feels_like(
                        temperature.convertKelvinToCelsius(
                                jsonObject.get("main").getAsJsonObject().get("feels_like").getAsFloat()
                        )
                )
                .temp_min(
                        temperature.convertKelvinToCelsius(
                                jsonObject.get("main").getAsJsonObject().get("temp_min").getAsFloat()
                        )
                )
                .temp_max(
                        temperature.convertKelvinToCelsius(
                                jsonObject.get("main").getAsJsonObject().get("temp_max").getAsFloat()
                        )
                )
                .pressure(
                        temperature.convertPascalToMillimetersOfMercury(
                                jsonObject.get("main").getAsJsonObject().get("pressure").getAsInt()
                        )
                )
                .humidity(
                        jsonObject.get("main").getAsJsonObject().get("humidity").getAsInt()
                )
                .build();

        return temperature;
    }


    public static Wind getWind(JsonObject jsonObject) {
        Wind wind = new Wind();

        wind = Wind.builder()
                .speed(
                        Math.round(jsonObject.get("wind").getAsJsonObject().get("speed").getAsFloat() * 100.0) / 100.0f
                )
                .degrees(
                        wind.convertDegreesToDirection(
                                jsonObject.get("wind").getAsJsonObject().get("deg").getAsFloat()
                        )
                )
                .build();

        return wind;
    }

    public static Weather getWeatherByCityName(JsonObject jsonObject) {
        Weather weather = Weather.builder()
                .weather(
                        jsonObject.get("weather").getAsJsonArray().get(0)
                                .getAsJsonObject().get("main").getAsString()
                )
                .description(
                        jsonObject.get("weather").getAsJsonArray().get(0)
                                .getAsJsonObject().get("description").getAsString()
                )
                .build();

        return weather;
    }
}
