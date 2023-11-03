package com.group.Gestion.src.controller;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.model.Status;
import com.group.Gestion.src.service.EmployeeService;
import com.group.Gestion.src.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class AdminDashboardController {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate, Model model) {
        if (selectedDate == null) {
            selectedDate = LocalDate.now().minusDays(1);  // Default to yesterday
        }
        int workingEmployeeCount = 0;
        int outsideEmployeeCount=0;
        int inBreakEmployeeCount=0;
        Status workingStatus = Status.EN_TRAVAIL;
        Status inBreakStatus= Status.EN_PAUSE;
        Status outsideStatus = Status.DEHORS;

        long totalEmployeeCount = employeeService.getTotalEmployeeCount();
        Map<String, Long> ratingStatistics = feedbackService.getRatingStatisticsForDate(selectedDate);
        List<Employee> employees = employeeService.getEmployees();

        for (Employee employee : employees) {
            if (employee.getStatus() == workingStatus) {
                workingEmployeeCount++;
            }
            if (employee.getStatus() == inBreakStatus) {
                inBreakEmployeeCount++;
            }
            if(employee.getStatus() == outsideStatus){
                outsideEmployeeCount++;
            }
        }
        model.addAttribute("workingEmployeeCount", workingEmployeeCount);
        model.addAttribute("outsideEmployeeCount", outsideEmployeeCount);
        model.addAttribute("inBreakEmployeeCount", inBreakEmployeeCount);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("totalEmployeeCount", totalEmployeeCount);
        model.addAttribute("ratingStatistics", ratingStatistics);

        return "dashboard";
    }
}
