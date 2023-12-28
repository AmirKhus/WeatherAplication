package ru.practicum.strategy;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static ru.practicum.util.Util.*;

public class CoordinatesWeatherInput implements WeatherInputStrategy {

    private final String weatherApiKey = propertyReader.getProperty("api.weatherApiKey");

    @Override
    public String getWeather(Map<String, String> informationAboutCity) {
        if (informationAboutCity != null)
            return getWeatherByCoordinates(
                    informationAboutCity.get("longitude"),
                    informationAboutCity.get("latitude"),
                    informationAboutCity.get("cityName")
            );
        else {
            String ip = getIPAddress();
            Map<String, String> getGeoLocation = getGeoLocation(ip);
            return new StringBuilder("IP: ").append(ip).append(";\n\n").append(
                    getWeatherByCoordinates(
                            getGeoLocation.get("longitude"),
                            getGeoLocation.get("latitude"),
                            getGeoLocation.get("cityName")
                    )
            ).toString();
        }
    }

    private String generatingUrlToGetWeatherByLongitudeAndLatitude(String longitude, String latitude) {
        StringBuilder urlGetWeatherByLatAndLon =
                new StringBuilder("https://api.openweathermap.org/data/2.5/weather?lat={LAT}&lon={LON}&appid={API_KEY}");

        replacePlaceholder(urlGetWeatherByLatAndLon, "{LON}", longitude);
        replacePlaceholder(urlGetWeatherByLatAndLon, "{LAT}", latitude);
        replacePlaceholder(urlGetWeatherByLatAndLon, "{API_KEY}", weatherApiKey);

        return urlGetWeatherByLatAndLon.toString();
    }

    public String getWeatherByCoordinates(String longitude, String latitude, String cityName) {
        InputStreamReader reader;

        String url = generatingUrlToGetWeatherByLongitudeAndLatitude(longitude, latitude);
        reader = getInputStreamReader(url);
        JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);

        return createWeatherInformation(jsonObject, cityName).toString();
    }

    private String getIPAddress() {
        URL url = null;
        try {
            url = new URL("https://httpbin.org/ip");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = null;
        String ipAddress = null;
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            ipAddress = response.toString().replaceAll("\\s+", "");
            ipAddress = ipAddress.substring(ipAddress.indexOf(":") + 2, ipAddress.length() - 2);

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ipAddress;
    }

    private Map<String, String> getGeoLocation(String targetIp) {
        String apiKey = propertyReader.getProperty("api.ipAddressApiKey");
        String apiUrl = "http://api.ipstack.com/" + targetIp + "?access_key=" + apiKey;

        InputStreamReader reader = getInputStreamReader(apiUrl);
        JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);

        Map<String, String> informationAboutCity = new HashMap<>();
        informationAboutCity.put("latitude", jsonObject.get("latitude").getAsString());
        informationAboutCity.put("longitude", jsonObject.get("longitude").getAsString());
        informationAboutCity.put("cityName", jsonObject.get("city").getAsString());

        return informationAboutCity;
    }
}
