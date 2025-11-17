package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoggingController {
    @GetMapping("/login")
    public String showLoginPage() {
        return "fancy-login";
    }
}