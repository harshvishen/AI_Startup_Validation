package com.utkarsh.startupvalidation.dto;

public class IdeaSuggestionRequest {
    private String suggestion;

    public IdeaSuggestionRequest() {}

    public IdeaSuggestionRequest(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
