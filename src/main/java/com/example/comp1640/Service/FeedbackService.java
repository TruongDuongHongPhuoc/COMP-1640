package com.example.comp1640.Service;

import com.example.comp1640.model.Contribution;
import com.example.comp1640.model.Feedback;
import com.example.comp1640.repository.ContributionRepository;
import com.example.comp1640.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    public List<Feedback> ReturnFeedBackWithContributionId(String id){
        List<Feedback> rawFeds = feedbackRepository.ReturnFeedBacks();

        // Filter the list based on contribution ID matching the provided parameter
        List<Feedback> filteredFeedbacks = rawFeds.stream()
                .filter(feedback -> feedback.getContributionId().equals(id))
                .collect(Collectors.toList());
        return filteredFeedbacks;
    }
}
