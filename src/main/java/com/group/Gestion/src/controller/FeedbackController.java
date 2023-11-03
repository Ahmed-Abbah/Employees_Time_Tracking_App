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
import java.util.Date;
import java.util.List;

@Controller
public class FeedbackController {
    @Autowired
    private FeedbackRepository feedbackRepository; // Assurez-vous d'avoir un repository pour la classe Feedback
    @GetMapping("/feedback-submitted")
    public ModelAndView feedbacksubmitted() {
        return new ModelAndView("submitfeedback");
    }


    @GetMapping("/feedback")
    public ModelAndView showFeedbackForm(HttpServletRequest request) {
        HttpSession session = request.getSession();
        long idEmploye = 1; // Vous devez stocker l'ID de l'employé en session

        if (idEmploye == 0) {
            // L'ID de l'employé n'est pas défini en session, redirigez-le vers une page de connexion ou affichez un message d'erreur.
            return new ModelAndView("redirect:/"); // Remplacez "login" par la page de connexion appropriée
        }

        // Vérifiez si l'employé a déjà rempli le formulaire aujourd'hui
        LocalDate currentDate = LocalDate.now();
        List<Feedback> feedbacks = feedbackRepository.findByDateAndIdEmploye(currentDate, idEmploye);

        if (!feedbacks.isEmpty()) {
            // L'employé a déjà rempli le formulaire pour aujourd'hui, affichez un message
            ModelAndView modelAndView = new ModelAndView("submitfeedback");
            modelAndView.addObject("message", "Vous avez déjà rempli le formulaire pour aujourd'hui.");
            return modelAndView;
        }

        // L'employé n'a pas encore rempli le formulaire aujourd'hui, affichez le formulaire
        return new ModelAndView("feedback");
    }


    @PostMapping("/submit-feedback")
    public String submitFeedback(String value, HttpServletRequest request) {
        // Obtenez l'ID de l'employé à partir de la session (ou d'une autre source)
        HttpSession session = request.getSession();
        long id_employe = 1; // Vous devez stocker l'ID de l'employé en session

        // Créez une instance de Feedback
        Feedback feedback = new Feedback();
        feedback.setDate(LocalDate.now()); // Utilisez LocalDate pour la date actuelle
        feedback.setValue(value);
        feedback.setIdEmploye(id_employe); // Associez l'ID de l'employé au feedback

        // Enregistrez le feedback dans la base de données
        feedbackRepository.save(feedback);

        // Redirigez l'utilisateur vers une page de confirmation
        return "redirect:/feedback-submitted"; // Assurez-vous de créer cette page
    }



}