package com.group.Gestion.src.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToWelcome(){
        return "redirect:/employee/welcome";
    }
}
