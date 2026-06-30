package com.utkarsh.startupvalidation.controller;

import com.utkarsh.startupvalidation.dto.IdeaRatingRequest;
import com.utkarsh.startupvalidation.dto.IdeaSuggestionRequest;
import com.utkarsh.startupvalidation.entity.IdeaRating;
import com.utkarsh.startupvalidation.entity.IdeaSuggestion;
import com.utkarsh.startupvalidation.service.IdeaInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ideas/{id}")
public class IdeaInteractionController {

    @Autowired
    private IdeaInteractionService interactionService;

    @PostMapping("/rate")
    public String rateIdea(@PathVariable Long id, @RequestBody IdeaRatingRequest request) {
        interactionService.rateIdea(id, request.getRating());
        return "Idea rated successfully";
    }

    @PostMapping("/suggestions")
    public IdeaSuggestion addSuggestion(@PathVariable Long id, @RequestBody IdeaSuggestionRequest request) {
        return interactionService.addSuggestion(id, request.getSuggestion());
    }

    @GetMapping("/suggestions")
    public List<IdeaSuggestion> getSuggestions(@PathVariable Long id) {
        return interactionService.getSuggestions(id);
    }
}
