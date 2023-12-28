package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Temperature {
    private int temp;
    private int feels_like;
    private int temp_min;
    private int temp_max;
    private int pressure;
    private int humidity;

    public int convertPascalToMillimetersOfMercury(double pascal) {
        return (int) (pascal * 0.750062);
    }

    public int convertKelvinToCelsius(double temp) {
        return (int) (temp - 272.15);
    }

    @Override
    public String toString() {
        return "Temperature:\n" +
                "Temp = " + temp + ";\n" +
                "Feels like = " + feels_like + ";\n" +
                "Temp minimum = " + temp_min + ";\n" +
                "Temp maximum = " + temp_max + ";\n" +
                "Pressure = " + pressure + ";\n" +
                "Humidity = " + humidity + ";\n";
    }
}
