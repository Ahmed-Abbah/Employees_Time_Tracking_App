package com.group.Gestion.src.controller;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.model.Status;
import com.group.Gestion.src.model.WorkDay;
import com.group.Gestion.src.security.SecurityController;
import com.group.Gestion.src.service.EmployeeService;
import com.group.Gestion.src.service.WorkDayService;
import com.group.Gestion.src.utilities.DateTimeProvider;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;
    private WorkDayService workDayService;

    @Autowired
    public EmployeeController(EmployeeService employeeService,WorkDayService workDayService){
        this.employeeService=employeeService;
        this.workDayService = workDayService;
    }
    @GetMapping("/welcome")
    public String returnWelcomeView(HttpSession http){
        if(!SecurityController.isUserAuthenticated(http)){
            return "redirect:/auth/login";
        }
        Employee employee =(Employee) http.getAttribute("loggedInEmployee");
        try{
            WorkDay workDay = workDayService.findWorkDayByDateAndEmployee(employee,DateTimeProvider.getCurrentDate());
            http.setAttribute("workDayHasEnded",false);
            if(workDay==null){
                employee.setStatus(Status.DEHORS);
                http.setAttribute("loggedInEmployee",employee);
            }else{
                if(workDay.getEndTime()!=null){
                    if(workDay.getEndTime()!=""){
                        http.setAttribute("workDayHasEnded",true);
                    }
                }
            }
        }catch(Exception e){
            http.setAttribute("workDayHasEnded",true);
        }
        return "welcome";
    }




}
