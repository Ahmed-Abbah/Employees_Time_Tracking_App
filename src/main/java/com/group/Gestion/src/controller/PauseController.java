package com.group.Gestion.src.controller;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.model.Pause;
import com.group.Gestion.src.model.Status;
import com.group.Gestion.src.model.WorkDay;
import com.group.Gestion.src.security.SecurityController;
import com.group.Gestion.src.service.EmployeeService;
import com.group.Gestion.src.service.PauseService;
import com.group.Gestion.src.service.WorkDayService;
import com.group.Gestion.src.utilities.DateTimeProvider;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/pause")
public class PauseController {
    private WorkDayService workDayService;
    private PauseService pauseService;
    private EmployeeService employeeService;
    @Autowired
    public PauseController(WorkDayService workDayService, PauseService pauseService, EmployeeService employeeService) {
        this.workDayService = workDayService;
        this.pauseService = pauseService;
        this.employeeService = employeeService;
    }

    @GetMapping("/startPause")
    public String startPause(HttpSession http){
        if(!SecurityController.isUserAuthenticated(http)){
            return "redirect:/auth/login";
        }
        Employee employee =(Employee) http.getAttribute("loggedInEmployee");
        WorkDay workDay = workDayService.findWorkDayByDateAndEmployee(employee,DateTimeProvider.getCurrentDate());
        System.out.println("workday foud date is : "+workDay.getDate());
        Pause pause = Pause.builder()
                .startTime(DateTimeProvider.getCurrentTime())
                .workDay(workDay)
                .build();
        System.out.println("Pause workday date is : "+pause.getWorkDay().getDate()+
                "\n Pause workday start time is : "+pause.getWorkDay().getStartTime()+
                "\n new Pause has started at : "+pause.getStartTime()+
                "\n for employee : "+pause.getWorkDay().getEmployee().getEmail()+
                "\n work day ID is : "+pause.getWorkDay().getId());
        pauseService.save(pause);
        employee.setStatus(Status.EN_PAUSE);
        http.setAttribute("loggedInEmployee",this.employeeService.save(employee,http));
        return "redirect:/employee/welcome";
    }

    @GetMapping("/endPause")
    public String endPause(HttpSession http){
        if(!SecurityController.isUserAuthenticated(http)){
            return "redirect:/auth/login";
        }
        Employee employee =(Employee) http.getAttribute("loggedInEmployee");
        WorkDay workDay = workDayService.findWorkDayByDateAndEmployee(employee,DateTimeProvider.getCurrentDate());
        for(Pause pause : workDay.getPauses()){
            if(pause.getEndTime()==null){
                pause.setEndTime(DateTimeProvider.getCurrentTime());
                this.pauseService.save(pause);
            }
        }

        employee.setStatus(Status.EN_TRAVAIL);
        http.setAttribute("loggedInEmployee",this.employeeService.save(employee,http));
        return "redirect:/employee/welcome";
    }
}
