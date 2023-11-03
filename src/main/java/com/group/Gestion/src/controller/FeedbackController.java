package com.group.Gestion.src.controller;

import com.group.Gestion.src.model.Feedback;
import com.group.Gestion.src.repository.FeedbackRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class FeedbackController {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/feedback-submitted")
    public ModelAndView feedbacksubmitted() {
        return new ModelAndView("submitfeedback");
    }

    @GetMapping("/feedback")
    public ModelAndView showFeedbackForm(HttpServletRequest request) {
        HttpSession session = request.getSession();
        long idEmploye = 1; // Vous devez stocker l'ID de l'employé en session

        if (idEmploye == 0) {
            return new ModelAndView("redirect:/"); // Redirigez vers la page de connexion appropriée
        }

        LocalDate currentDate = LocalDate.now();
        List<Feedback> feedbacks = feedbackRepository.findByDateAndIdEmploye(currentDate, idEmploye);

        if (!feedbacks.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("submitfeedback");
            modelAndView.addObject("message", "Vous avez déjà rempli le formulaire pour aujourd'hui.");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("feedback");
        modelAndView.addObject("currentDate", currentDate);
        return modelAndView;
    }

    @PostMapping("/submit-feedback")
    public String submitFeedback(String value, String rate, HttpServletRequest request) {
        HttpSession session = request.getSession();
        long idEmploye = 1; // Vous devez stocker l'ID de l'employé en session

        Feedback feedback = new Feedback();
        feedback.setDate(LocalDate.now());
        feedback.setValue(value);
        feedback.setRate(rate);
        feedback.setIdEmploye(idEmploye);

        feedbackRepository.save(feedback);

        return "redirect:/feedback-submitted";
    }
}
