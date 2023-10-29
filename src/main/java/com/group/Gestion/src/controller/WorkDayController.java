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

import java.util.List;

@Controller
@RequestMapping("/workDay")
public class WorkDayController {
    private WorkDayService workDayService;
    private EmployeeService employeeService;

    @Autowired
    public WorkDayController(WorkDayService workDayService, EmployeeService employeeService) {
        this.workDayService = workDayService;
        this.employeeService = employeeService;
    }

    @GetMapping("/startDay")
    public String startDay(HttpSession http){

        if(!SecurityController.isUserAuthenticated(http)){
            return "redirect:/auth/login";
        }

        Employee employee = (Employee) http.getAttribute("loggedInEmployee");



        WorkDay workDay = WorkDay.builder()
                .startTime(DateTimeProvider.getCurrentTime())
                .date(DateTimeProvider.getCurrentDate())
                .employee(employee)
                .build();
        employee.setStatus(Status.EN_TRAVAIL);
        workDay.setEmployee(employee);
        this.workDayService.save(workDay);
        this.employeeService.save(employee);

        return "redirect:/employee/welcome";
    }


    @GetMapping("/endDay")
    public String endDay(HttpSession http){
        if(!SecurityController.isUserAuthenticated(http)){
            return "redirect:/auth/login";
        }
        Employee employee = (Employee) http.getAttribute("loggedInEmployee");
        WorkDay workDay = workDayService.findWorkDayByDateAndEmployee(employee,DateTimeProvider.getCurrentDate());
        System.out.println("work day end time : "+workDay.getEndTime()+"\n workday start time : "+workDay.getStartTime());
        workDay.setEndTime(DateTimeProvider.getCurrentTime());
        workDay.setEmployee(employee);
        this.workDayService.save(workDay);
        Employee employee1 = employeeService.findEmployeeById(employee.getId());
        employee.setStatus(Status.DEHORS);
        this.employeeService.save(employee1);
        return "redirect:/employee/welcome";
    }
}
