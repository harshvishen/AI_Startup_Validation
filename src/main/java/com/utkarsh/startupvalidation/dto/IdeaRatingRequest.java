package com.utkarsh.startupvalidation.dto;

public class IdeaRatingRequest {
    private int rating;

    public IdeaRatingRequest() {}

    public IdeaRatingRequest(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
