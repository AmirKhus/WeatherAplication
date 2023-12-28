package ru.practicum;

import ru.practicum.service.WeatherService;
import ru.practicum.strategy.CityNameWeatherInput;
import ru.practicum.strategy.CoordinatesWeatherInput;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main extends HttpServlet {
    public static void main(String[] args) {
        String weatherInfo;
        Scanner scanner = new Scanner(System.in);
        Map<String, String> informationAboutWeatherService;
        WeatherService weatherService = new WeatherService();
        while (true) {
            System.out.println("Enter the function number from the menu:");
            System.out.println("1. Determine your location by IP");
            System.out.println("2. Entering the city name yourself");
            System.out.println("3. Exit");
            System.out.print("> ");
            int choice = getChoice(scanner);
            switch (choice) {
                case 1 -> {
                    weatherService.setInputStrategy(new CoordinatesWeatherInput());
                    weatherInfo = weatherService.getWeather();
                    System.out.println(weatherInfo);
                }
                case 2 -> {
                    informationAboutWeatherService = new HashMap<>();
                    weatherService = new WeatherService(new CityNameWeatherInput());
                    informationAboutWeatherService.put("cityName", handleCustomCityInput(scanner));
                    weatherInfo = weatherService.getWeather(informationAboutWeatherService);
                    System.out.println(weatherInfo);
                }
                case 3 -> {
                    System.out.println("Exit the program.");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Wrong choice. Please enter 1 or 2.");
            }
        }
    }

    private static int getChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Enter an integer.");
            System.out.print(">");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static String handleCustomCityInput(Scanner scanner) {
        System.out.print("Enter the name of the city: ");
        String cityName = scanner.next();
        return cityName;
    }
}