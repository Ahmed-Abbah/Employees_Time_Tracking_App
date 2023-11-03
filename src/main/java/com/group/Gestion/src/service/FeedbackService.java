package com.group.Gestion.src.service;

import com.group.Gestion.src.model.Feedback;
import com.group.Gestion.src.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getFeedbackDataForDate(LocalDate date) {
        return feedbackRepository.findByDate(date);
    }



    public Map<String, Long> getRatingStatisticsForDate(LocalDate date) {
        List<Feedback> feedbackData = getFeedbackDataForDate(date);
        Map<String, Long> ratingStatistics = new HashMap<>();

        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            long count = feedbackData.stream().filter(f -> f.getRate().equals(String.valueOf(finalI))).count();
            ratingStatistics.put("Rating " + i, count);
        }

        return ratingStatistics;
    }
}
