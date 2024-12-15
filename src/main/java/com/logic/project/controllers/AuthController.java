package com.logic.project.controllers;

import com.logic.project.models.User;
import com.logic.project.services.AuthenticationService;
import com.logic.project.services.SessionService;
import com.logic.project.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.UUID;

@Controller
@Log4j2
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) {
            for(ObjectError error : bindingResult.getAllErrors()) {
                log.error(error.getDefaultMessage());
            }
            return "registration";
        }
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "authentification";
    }

    @PostMapping("/login")
    public String loginUser(
            @ModelAttribute("user") User user,
            BindingResult bindingResult,
            HttpServletResponse response
    ){
        if(bindingResult.hasErrors()) {
            for(ObjectError error : bindingResult.getAllErrors()) {
                log.error(error.getDefaultMessage());
            }
            return "authentification";
        }
        Optional<User> userOptional = userService.getUserByLoginAndPassword(
                user.getLogin(), user.getPassword()
        );
        if(userOptional.isPresent()) {
            User dbUser = userOptional.get();
            UUID sessionId = sessionService.createSessionForUser(dbUser);
            Cookie cookie = new Cookie("sessionId", String.valueOf(sessionId));
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setPath("/");
            cookie.setHttpOnly(true);  // Prevent JavaScript access
            cookie.setSecure(true);    // Only send over HTTPS
            response.addCookie(cookie);
        }else{
            return "authentification";
        }

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.invalidateSession(request, response); //Use AuthenticationService
        return "redirect:/";
    }
}
