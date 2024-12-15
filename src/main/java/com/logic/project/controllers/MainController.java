package com.logic.project.controllers;


import com.logic.project.models.Location;
import com.logic.project.models.User;
import com.logic.project.models.WeatherData;
import com.logic.project.services.AuthenticationService;
import com.logic.project.services.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
public class MainController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LocationService locationService;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("isAuthenticated", false);
        User currentAuthentificatedUser = authenticationService.getAuthenticatedUser(request);
        if (currentAuthentificatedUser != null) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("username", currentAuthentificatedUser.getLogin());

            List<Location> userLocations = locationService.getAllUserLocations(
                    currentAuthentificatedUser.getId()
            );

            List<WeatherData> weatherDataList = new ArrayList<>();
            for (Location location : userLocations) {
                weatherDataList.add(locationService.getWeather(location.getName()));
            }
            model.addAttribute("weatherList", weatherDataList);
        }
        return "home";
    }
}
