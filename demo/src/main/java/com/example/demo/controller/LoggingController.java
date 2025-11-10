package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoggingController {
    @GetMapping("/login")
    public String login(){
        return "fancy-login";
    }
    @GetMapping("/access-denied")
    public String accessDenied(){
        return "access-denied";
    }
}
