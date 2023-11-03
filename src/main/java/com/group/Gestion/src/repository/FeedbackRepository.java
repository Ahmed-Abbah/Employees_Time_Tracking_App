package com.group.Gestion.src.repository;

import com.group.Gestion.src.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {



    // Méthode pour rechercher les feedbacks par date et ID de l'employé
    List<Feedback> findByDateAndIdEmploye(LocalDate date, Long idEmploye);

    List<Feedback> findByDate(LocalDate date);
    long count();
}