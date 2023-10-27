package com.group.Gestion.src.controller;

import com.group.Gestion.src.security.SecurityController;
import com.group.Gestion.src.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }
    @GetMapping("/welcome")
    public String test2(HttpSession http){
        if(!SecurityController.isUserAuthenticated(http)){
            return "redirect:/auth/login";
        }
        return "welcome";
    }




}
