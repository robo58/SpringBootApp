package com.example.webapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping("/")
    public String showHome(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
