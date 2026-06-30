package com.utkarsh.startupvalidation.controller;

import com.utkarsh.startupvalidation.dto.DashboardStatsResponse;
import com.utkarsh.startupvalidation.dto.ValidationResponse;
import com.utkarsh.startupvalidation.entity.StartupIdea;
import com.utkarsh.startupvalidation.service.StartupIdeaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ideas")
public class StartupIdeaController {

    @Autowired
    private StartupIdeaService startupIdeaService;

    @PostMapping
    public StartupIdea createIdea(
            @RequestBody StartupIdea idea) {

        return startupIdeaService.createIdea(idea);
    }

    @GetMapping
    public List<StartupIdea> getAllIdeas() {

        return startupIdeaService.getAllIdeas();
    }

    @GetMapping("/explore")
    public com.utkarsh.startupvalidation.dto.FeedResponse getExploreFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {
        return startupIdeaService.getExploreFeed(page, size, search);
    }

    @GetMapping("/following")
    public com.utkarsh.startupvalidation.dto.FeedResponse getFollowingFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return startupIdeaService.getFollowingFeed(page, size);
    }

    @GetMapping("/{id}")
    public StartupIdea getIdeaById(
            @PathVariable Long id) {

        return startupIdeaService.getIdeaById(id);
    }

    @PutMapping("/{id}")
    public StartupIdea updateIdea(
            @PathVariable Long id,
            @RequestBody StartupIdea idea) {

        return startupIdeaService.updateIdea(id, idea);
    }

    @DeleteMapping("/{id}")
    public String deleteIdea(
            @PathVariable Long id) {

        startupIdeaService.deleteIdea(id);

        return "Idea Deleted Successfully";
    }

    @GetMapping("/validate/{id}")
    public ValidationResponse validateIdea(
            @PathVariable Long id) {

        return startupIdeaService.validateIdea(id);
    }

    @GetMapping("/industry/{industry}")
    public List<StartupIdea> getIdeasByIndustry(
            @PathVariable String industry) {

        return startupIdeaService
                .getIdeasByIndustry(industry);
    }

    @GetMapping("/stats")
    public DashboardStatsResponse getStats() {

        return startupIdeaService.getStats();
    }
}