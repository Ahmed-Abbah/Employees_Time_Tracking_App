package com.group.Gestion.src.controller;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.model.Feedback;
import com.group.Gestion.src.repository.FeedbackRepository;
import com.group.Gestion.src.security.SecurityController;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;

@Controller
public class FeedbackController {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/employee/feedback-submitted")
    public ModelAndView feedbackSubmitted() {
        return new ModelAndView("submitfeedback");
    }

    @GetMapping("/employee/feedback")
    public String showFeedbackForm() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Employee) {
            return "/employee/feedback"; // Afficher la page de formulaire
        } else {
            return "redirect:/"; // Rediriger vers la page de login
        }
    }

    @PostMapping("/employee/submit-feedback")
    public String submitFeedback(String value, String rate,HttpSession http) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (SecurityController.isUserAuthenticated(http)) {
            Employee employee = (Employee) http.getAttribute("loggedInEmployee");
            Long idEmploye = employee.getId(); // Supposons que votre classe Employee a une méthode pour obtenir l'ID de l'employé.

            Feedback feedback = new Feedback();
            feedback.setDate(LocalDate.now());
            feedback.setValue(value);
            feedback.setRate(rate);
            feedback.setIdEmploye(idEmploye);

            feedbackRepository.save(feedback);

            return "redirect:/employee/feedback-submitted"; // Rediriger vers la page de confirmation
        } else {
            return "redirect:/"; // Rediriger vers la page de login
        }
    }
}
