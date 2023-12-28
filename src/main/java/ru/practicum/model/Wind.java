package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wind {
    private float speed;
    private String degrees;

    public String convertDegreesToDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5) {
            return "North";
        } else if (degrees >= 22.5 && degrees < 67.5) {
            return "North-East";
        } else if (degrees >= 67.5 && degrees < 112.5) {
            return "East";
        } else if (degrees >= 112.5 && degrees < 157.5) {
            return "South-East";
        } else if (degrees >= 157.5 && degrees < 202.5) {
            return "South";
        } else if (degrees >= 202.5 && degrees < 247.5) {
            return "South-West";
        } else if (degrees >= 247.5 && degrees < 292.5) {
            return "West";
        } else if (degrees >= 292.5 && degrees < 337.5) {
            return "North-West";
        } else {
            return "Unknown";
        }
    }

    @Override
    public String toString() {
        return "Wind:\n" +
                "Speed = " + speed + ";\n" +
                "Degrees = " + degrees + ";\n";
    }
}
