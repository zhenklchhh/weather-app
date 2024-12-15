package com.logic.project.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logic.project.models.Location;
import com.logic.project.models.WeatherData;
import com.logic.project.repositories.LocationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@Log4j2
@Transactional
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    private static final String API_KEY = "f605275704a9d0782693542347ec49d8";
    private static final String GEO_URL = "http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=metric";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public WeatherData getWeather(String locationName) {
        try {
            URI geoUri = URI.create(String.format(GEO_URL, locationName, API_KEY));
            HttpRequest geoRequest = HttpRequest.newBuilder().uri(geoUri).build();
            HttpResponse<String> geoResponse = httpClient.send(geoRequest, HttpResponse.BodyHandlers.ofString());

            if (geoResponse.statusCode() != 200) {
                throw new RuntimeException("Ошибка поиска локации: " + geoResponse.statusCode());
            }

            JsonNode geoJson = objectMapper.readTree(geoResponse.body());
            if (geoJson.isArray() && geoJson.size() > 0) {
                JsonNode firstLocation = geoJson.get(0);
                double lat = firstLocation.get("lat").asDouble();
                double lon = firstLocation.get("lon").asDouble();

                URI weatherUri = URI.create(String.format(WEATHER_URL, lat, lon, API_KEY));
                HttpRequest weatherRequest = HttpRequest.newBuilder().uri(weatherUri).build();
                HttpResponse<String> weatherResponse = httpClient.send(weatherRequest, HttpResponse.BodyHandlers.ofString());

                if (weatherResponse.statusCode() != 200) {
                    throw new RuntimeException("Ошибка получения погоды: " + weatherResponse.statusCode());
                }

                JsonNode weatherJson = objectMapper.readTree(weatherResponse.body());
                JsonNode mainNode = weatherJson.path("main");
                JsonNode weatherNode = weatherJson.path("weather");

                if (mainNode.isMissingNode() || weatherNode.isMissingNode()) {
                    throw new RuntimeException("Missing 'main' or 'weather' in the weather data.");
                }
                double temperature = mainNode.path("temp").asDouble();
                String description = weatherNode.get(0).get("description").asText();

                return new WeatherData(temperature, description, locationName, weatherJson);
            } else {
                throw new RuntimeException("Локация не найдена.");
            }
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    public Location getLocation(String locationName) throws IOException, InterruptedException {
        URI geoUri = URI.create(String.format(GEO_URL, locationName, API_KEY));
        HttpRequest geoRequest = HttpRequest.newBuilder().uri(geoUri).build();
        HttpResponse<String> geoResponse = httpClient.send(geoRequest, HttpResponse.BodyHandlers.ofString());

        if (geoResponse.statusCode() != 200) {
            throw new RuntimeException("Error finding location: " + geoResponse.statusCode());
        }

        JsonNode geoJson = objectMapper.readTree(geoResponse.body());
        Location location;
        if (geoJson.isArray() && geoJson.size() > 0) {
            JsonNode firstLocation = geoJson.get(0);
            String name = firstLocation.get("name").asText();
            double lat = firstLocation.get("lat").asDouble();
            double lon = firstLocation.get("lon").asDouble();
            location = new Location(name, lat, lon);
        } else {
            throw new RuntimeException("Location not found");
        }
        return location;
    }

    public List<Location> getAllUserLocations(long userId) {
        return locationRepository.findAllByUserId(userId);
    }

    @Transactional
    public void saveLocation(Location location) {
        locationRepository.save(location);
    }
}
