package com.utkarsh.startupvalidation.dto;

import java.util.List;

public class FeedResponse {
    private List<StartupIdeaResponse> ideas;
    private int totalPages;
    private long totalElements;

    public FeedResponse() {}

    public FeedResponse(List<StartupIdeaResponse> ideas, int totalPages, long totalElements) {
        this.ideas = ideas;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<StartupIdeaResponse> getIdeas() { return ideas; }
    public void setIdeas(List<StartupIdeaResponse> ideas) { this.ideas = ideas; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public long getTotalElements() { return totalElements; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }
}
