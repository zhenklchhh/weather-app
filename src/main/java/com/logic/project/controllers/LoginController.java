package com.logic.project.controllers;

import com.logic.project.models.User;
import com.logic.project.services.UserService;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "authorization";
    }

    @PostMapping
    public String loginUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) {
            for(ObjectError error : bindingResult.getAllErrors()) {
                log.error(error.getDefaultMessage());
            }
            return "authorization";
        }
        return "redirect:/";
    }
}
