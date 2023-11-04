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
    public ModelAndView feedbackSubmitted(HttpSession http) {
        http.invalidate();
        return new ModelAndView("submitfeedback");
    }

    @GetMapping("/employee/feedback")
    public String showFeedbackForm(HttpSession http) {
        if (SecurityController.isUserAuthenticated(http)) {
            Employee employee = (Employee) http.getAttribute("loggedInEmployee");
            if (hasAlreadySubmittedFeedback(employee)) {
                // L'utilisateur a déjà soumis un formulaire pour aujourd'hui
                return "redirect:/employee/feedback-submitted";
            }
            return "feedback"; // Afficher la page de formulaire
        } else {
            http.setAttribute("hasToSubmitForm", true);
            return "redirect:/"; // Rediriger vers la page de login
        }
    }

    @PostMapping("/employee/submit-feedback")
    public String submitFeedback(String value, String rate, HttpSession http) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (SecurityController.isUserAuthenticated(http)) {
            Employee employee = (Employee) http.getAttribute("loggedInEmployee");
            if (hasAlreadySubmittedFeedback(employee)) {
                // L'utilisateur a déjà soumis un formulaire pour aujourd'hui
                return "redirect:/employee/feedback-submitted";
            }

            Feedback feedback = new Feedback();
            feedback.setDate(LocalDate.now());
            feedback.setValue(value);
            feedback.setRate(rate);
            feedback.setIdEmploye(employee.getId());

            feedbackRepository.save(feedback);

            return "redirect:/employee/feedback-submitted"; // Rediriger vers la page de confirmation
        } else {
            http.setAttribute("hasToSubmitForm", true);
            return "redirect:/"; // Rediriger vers la page de login
        }
    }

    private boolean hasAlreadySubmittedFeedback(Employee employee) {
        LocalDate currentDate = LocalDate.now();
        List<Feedback> feedbacks = feedbackRepository.findByDateAndIdEmploye(currentDate, employee.getId());
        return !feedbacks.isEmpty();
    }
}
