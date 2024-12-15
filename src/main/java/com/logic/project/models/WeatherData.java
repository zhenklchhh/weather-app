package com.logic.project.models;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherData {
    private double temperature;
    private String description;
    private String locationName;
    private JsonNode weatherJson;
}
