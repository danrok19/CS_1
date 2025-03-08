package com.example.CS_1.controller;

import com.example.CS_1.entity.User;
import com.example.CS_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @GetMapping("/messages-form")
    public String messageFormPage(){
        return "messages-form";
    }


    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email) {
        userService.registerUser(username, password, email);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "login";
    }
}
