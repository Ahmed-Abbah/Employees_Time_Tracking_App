package com.group.Gestion.src.controller;

import com.group.Gestion.src.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }


    @GetMapping("/test")
    public String test(){
        return "Login";
    }

    @GetMapping("/welcome")
    public String test2(){
        return "welcome";
    }




}
