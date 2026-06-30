package com.utkarsh.startupvalidation.dto;

public class ValidationResponse {

    private int marketScore;
    private int competitionScore;
    private int riskScore;
    private String verdict;
    private String feedback;

    public ValidationResponse() {
    }

    public ValidationResponse(
            int marketScore,
            int competitionScore,
            int riskScore,
            String verdict,
            String feedback) {

        this.marketScore = marketScore;
        this.competitionScore = competitionScore;
        this.riskScore = riskScore;
        this.verdict = verdict;
        this.feedback = feedback;
    }

    public int getMarketScore() {
        return marketScore;
    }

    public void setMarketScore(int marketScore) {
        this.marketScore = marketScore;
    }

    public int getCompetitionScore() {
        return competitionScore;
    }

    public void setCompetitionScore(int competitionScore) {
        this.competitionScore = competitionScore;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}