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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


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
                        http.setAttribute("workDayHasEnded",true);
                }
            }
        }catch(Exception e){
            http.setAttribute("workDayHasEnded",true);
        }
        if(employee.getEmail().equals("admin@admin.com")){
            return "redirect:/employee/admin/employeesList";
        }
        return "welcome";
    }

    @GetMapping("/admin/employeesList")
    public  String listEmployee(Model model){
        List<Employee> employees = employeeService.getEmployees();
        model.addAttribute("employees",employees);
        return "ListEmployee";
    }



    @GetMapping("/admin/rapport")
    public  String rapportEmployee(@RequestParam("id") long id,Model model){
        Employee employee = employeeService.findEmployeeById(id);
        try{

//                    while(employee.getWorkDays().size()>8){
//
//            }

            model.addAttribute("employee",employee);
            return "rapport";
        }catch(Exception e){

        }
        return "rapport";
    }

}
