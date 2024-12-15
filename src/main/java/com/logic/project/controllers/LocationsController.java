package com.logic.project.controllers;


import com.logic.project.models.Location;
import com.logic.project.models.User;
import com.logic.project.models.WeatherData;
import com.logic.project.services.AuthenticationService;
import com.logic.project.services.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
@RequestMapping("/locations")
public class LocationsController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    @ResponseBody
    public ModelAndView getWeather(@RequestParam("locationName") String locationName,
                                   HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("weatherResult");
        User currentAuthentificatedUser = authenticationService.getAuthenticatedUser(request);
        modelAndView.addObject("isAuthenticated", currentAuthentificatedUser != null);
        
        try {
            WeatherData weatherData = locationService.getWeather(locationName);

            modelAndView.addObject("temperature", weatherData.getTemperature());
            modelAndView.addObject("description", weatherData.getDescription());
            modelAndView.addObject("locationName", weatherData.getLocationName());
            modelAndView.addObject("weatherData", weatherData.getWeatherJson());

        } catch (Exception e) {
            modelAndView.addObject("error", "Ошибка: " + e.getMessage());
        }

        return modelAndView;
    }

    @PostMapping("/favorites")
    @ResponseBody
    public ResponseEntity<Location> addLocationToFavorites(@RequestParam("locationName") String locationName,
                                                           HttpServletRequest request) {
        try {
            Location location = locationService.getLocation(locationName);
            location.setUserID(authenticationService.getAuthenticatedUser(request).getId());
            log.info(location.getLatitude() + " " + location.getLongitude());
            locationService.saveLocation(location);
            return new ResponseEntity<>(location, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error adding to favorites: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
