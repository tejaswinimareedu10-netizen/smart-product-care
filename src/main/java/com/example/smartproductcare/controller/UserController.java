package com.example.smartproductcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.smartproductcare.entity.User;
import com.example.smartproductcare.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") User user,
            Model model) {

        if (userService.emailExists(user.getEmail())) {

            model.addAttribute("error", "Email already exists");

            return "register";
        }

        userService.registerUser(user);

        model.addAttribute("success",
                "Registration Successful. Please Login.");

        return "login";
    }
}