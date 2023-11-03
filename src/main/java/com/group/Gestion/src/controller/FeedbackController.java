package com.group.Gestion.src.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedbackController {
    @GetMapping("/feedback")
    public String index(){
        return "feedback";
    }

}
