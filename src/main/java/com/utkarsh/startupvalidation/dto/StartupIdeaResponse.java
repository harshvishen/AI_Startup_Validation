package com.utkarsh.startupvalidation.dto;

import java.time.LocalDateTime;

public class StartupIdeaResponse {

    private Long id;
    private String title;
    private String industry;
    private String problem;
    private String solution;
    private LocalDateTime createdAt;
    private Long authorId;
    private String authorName;
    private Double averageRating;

    public StartupIdeaResponse() {
    }

    public StartupIdeaResponse(
            Long id,
            String title,
            String industry,
            String problem,
            String solution,
            LocalDateTime createdAt,
            Long authorId,
            String authorName,
            Double averageRating) {

        this.id = id;
        this.title = title;
        this.industry = industry;
        this.problem = problem;
        this.solution = solution;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.authorName = authorName;
        this.averageRating = averageRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}