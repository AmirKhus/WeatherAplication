package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    private String weather;
    private String description;

    @Override
    public String toString() {
        return "Weather:\n" +
                "Weather = " + weather + ";\n" +
                "Description = " + description + ";\n";
    }
}
