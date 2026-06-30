package com.utkarsh.startupvalidation.dto;

public class DashboardStatsResponse {

    private long totalIdeas;
    private long aiIdeas;
    private long edTechIdeas;

    public DashboardStatsResponse() {
    }

    public DashboardStatsResponse(
            long totalIdeas,
            long aiIdeas,
            long edTechIdeas) {

        this.totalIdeas = totalIdeas;
        this.aiIdeas = aiIdeas;
        this.edTechIdeas = edTechIdeas;
    }

    public long getTotalIdeas() {
        return totalIdeas;
    }

    public void setTotalIdeas(long totalIdeas) {
        this.totalIdeas = totalIdeas;
    }

    public long getAiIdeas() {
        return aiIdeas;
    }

    public void setAiIdeas(long aiIdeas) {
        this.aiIdeas = aiIdeas;
    }

    public long getEdTechIdeas() {
        return edTechIdeas;
    }

    public void setEdTechIdeas(long edTechIdeas) {
        this.edTechIdeas = edTechIdeas;
    }
}