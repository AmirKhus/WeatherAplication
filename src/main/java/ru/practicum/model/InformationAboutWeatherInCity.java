package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformationAboutWeatherInCity {
    private String nameCity;
    private Weather weather;
    private Temperature temperature;
    private Wind wind;

    @Override
    public String toString() {
        return "Information about weather in city:\n" +
                "City name = " + nameCity + ";\n\n" +
                weather + "\n" +
                temperature + "\n" +
                wind;
    }
}
