package com.utkarsh.startupvalidation.service;

import com.utkarsh.startupvalidation.entity.IdeaRating;
import com.utkarsh.startupvalidation.entity.IdeaSuggestion;
import com.utkarsh.startupvalidation.entity.StartupIdea;
import com.utkarsh.startupvalidation.entity.User;
import com.utkarsh.startupvalidation.repository.IdeaRatingRepository;
import com.utkarsh.startupvalidation.repository.IdeaSuggestionRepository;
import com.utkarsh.startupvalidation.repository.StartupIdeaRepository;
import com.utkarsh.startupvalidation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IdeaInteractionService {

    @Autowired
    private IdeaRatingRepository ratingRepository;

    @Autowired
    private IdeaSuggestionRepository suggestionRepository;

    @Autowired
    private StartupIdeaRepository ideaRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public IdeaRating rateIdea(Long ideaId, int ratingValue) {
        if(ratingValue < 1 || ratingValue > 5) throw new RuntimeException("Rating must be between 1 and 5");
        User user = getCurrentUser();
        StartupIdea idea = ideaRepository.findById(ideaId).orElseThrow(() -> new RuntimeException("Idea not found"));

        IdeaRating rating = ratingRepository.findByIdeaAndUser(idea, user).orElse(new IdeaRating());
        rating.setIdea(idea);
        rating.setUser(user);
        rating.setRating(ratingValue);
        return ratingRepository.save(rating);
    }

    public IdeaSuggestion addSuggestion(Long ideaId, String suggestionText) {
        User user = getCurrentUser();
        StartupIdea idea = ideaRepository.findById(ideaId).orElseThrow(() -> new RuntimeException("Idea not found"));

        IdeaSuggestion suggestion = new IdeaSuggestion();
        suggestion.setIdea(idea);
        suggestion.setUser(user);
        suggestion.setSuggestion(suggestionText);
        suggestion.setCreatedAt(LocalDateTime.now());
        return suggestionRepository.save(suggestion);
    }

    public List<IdeaSuggestion> getSuggestions(Long ideaId) {
        return suggestionRepository.findByIdeaIdOrderByCreatedAtDesc(ideaId);
    }
    
    public Double getAverageRating(Long ideaId) {
        Double avg = ratingRepository.getAverageRatingByIdeaId(ideaId);
        return avg != null ? avg : 0.0;
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteAllInteractionsForIdea(Long ideaId) {
        StartupIdea idea = ideaRepository.findById(ideaId).orElse(null);
        if (idea != null) {
            ratingRepository.deleteByIdea(idea);
            suggestionRepository.deleteByIdea(idea);
        }
    }
}
