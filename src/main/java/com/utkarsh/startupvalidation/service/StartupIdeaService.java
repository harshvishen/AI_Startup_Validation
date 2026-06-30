package com.utkarsh.startupvalidation.service;
import com.utkarsh.startupvalidation.dto.DashboardStatsResponse;
import com.utkarsh.startupvalidation.dto.ValidationResponse;
import com.utkarsh.startupvalidation.entity.StartupIdea;
import com.utkarsh.startupvalidation.repository.StartupIdeaRepository;

import com.utkarsh.startupvalidation.dto.FeedResponse;
import com.utkarsh.startupvalidation.dto.StartupIdeaResponse;
import com.utkarsh.startupvalidation.entity.User;
import com.utkarsh.startupvalidation.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StartupIdeaService {

    @Autowired
    private StartupIdeaRepository startupIdeaRepository;

    @Autowired
    private MarketAnalysisService marketAnalysisService;

    @Autowired
    private CompetitionAnalysisService competitionAnalysisService;

    @Autowired
    private RiskAnalysisService riskAnalysisService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdeaInteractionService ideaInteractionService;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public StartupIdea createIdea(StartupIdea idea) {
        validateIdeaSemantics(idea);
        
        idea.setAuthor(getCurrentUser());
        idea.setCreatedAt(LocalDateTime.now());

        return startupIdeaRepository.save(idea);
    }

    private void validateIdeaSemantics(StartupIdea idea) {
        if (geminiApiKey != null && !geminiApiKey.isEmpty() && !geminiApiKey.equals("YOUR_GEMINI_API_KEY")) {
            try {
                org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
                String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=" + geminiApiKey;

                String prompt = "You are a text quality checker.\n\n" +
                        "Check whether the following startup idea contains meaningful,\n" +
                        "human-written content.\n\n" +
                        "Title:\n" + idea.getTitle() + "\n\n" +
                        "Problem:\n" + idea.getProblem() + "\n\n" +
                        "Solution:\n" + idea.getSolution() + "\n\n" +
                        "Rules:\n" +
                        "1. If the text is meaningful, return ONLY:\nVALID\n\n" +
                        "2. If the text contains random characters, keyboard smashing,\n" +
                        "gibberish, meaningless words, or nonsensical content, return ONLY:\nINVALID";

                String safePrompt = prompt.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
                String requestBody = "{ \"contents\": [{ \"parts\":[{\"text\": \"" + safePrompt + "\"}] }] }";

                org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
                headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(requestBody, headers);

                org.springframework.http.ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(response.getBody());
                String textResponse = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText().trim();

                if (textResponse.equals("INVALID")) {
                    throw new IllegalArgumentException("Please enter a meaningful startup idea.");
                }
            } catch (IllegalArgumentException e) {
                throw e; // Pass to controller
            } catch (Exception e) {
                System.err.println("Gemini semantic validation failed: " + e.getMessage());
            }
        }
    }

    public List<StartupIdea> getAllIdeas() {

        return startupIdeaRepository.findAll();
    }

    // NEW METHOD
    public List<StartupIdea> getIdeasByIndustry(
            String industry) {

        return startupIdeaRepository
                .findByIndustry(industry);
    }

    public StartupIdea getIdeaById(Long id) {

        return startupIdeaRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Idea Not Found"));
    }

    public StartupIdea updateIdea(
            Long id,
            StartupIdea updatedIdea) {

        StartupIdea idea = startupIdeaRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Idea Not Found"));

        if (!idea.getAuthor().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized: You are not the author");
        }

        idea.setTitle(updatedIdea.getTitle());
        idea.setIndustry(updatedIdea.getIndustry());
        idea.setProblem(updatedIdea.getProblem());
        idea.setSolution(updatedIdea.getSolution());

        return startupIdeaRepository.save(idea);
    }

    public void deleteIdea(Long id) {
        StartupIdea idea = startupIdeaRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Idea Not Found"));

        if (!idea.getAuthor().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized: You are not the author");
        }

        ideaInteractionService.deleteAllInteractionsForIdea(id);

        startupIdeaRepository.deleteById(id);
    }

    @org.springframework.beans.factory.annotation.Value("${gemini.api.key:}")
    private String geminiApiKey;

    public ValidationResponse validateIdea(Long id) {

        StartupIdea idea = startupIdeaRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Idea Not Found"));

        if (geminiApiKey != null && !geminiApiKey.isEmpty() && !geminiApiKey.equals("YOUR_GEMINI_API_KEY")) {
            try {
                org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
                String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=" + geminiApiKey;

                String prompt = "You are a text quality checker.\n\n" +
                        "Check whether the following startup idea contains meaningful,\n" +
                        "human-written content.\n\n" +
                        "Title:\n" + idea.getTitle() + "\n\n" +
                        "Problem:\n" + idea.getProblem() + "\n\n" +
                        "Solution:\n" + idea.getSolution() + "\n\n" +
                        "Rules:\n" +
                        "1. If the text is meaningful, return ONLY:\nVALID\n\n" +
                        "2. If the text contains random characters, keyboard smashing,\n" +
                        "gibberish, meaningless words, or nonsensical content, return ONLY:\nINVALID";

                String safePrompt = prompt.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
                String requestBody = "{ \"contents\": [{ \"parts\":[{\"text\": \"" + safePrompt + "\"}] }] }";

                org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
                headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(requestBody, headers);

                org.springframework.http.ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(response.getBody());
                String textResponse = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText().trim();

                if (textResponse.equals("INVALID")) {
                    throw new IllegalArgumentException("Please enter a meaningful startup idea.");
                }
            } catch (IllegalArgumentException e) {
                throw e; // Pass to controller
            } catch (Exception e) {
                System.err.println("Gemini semantic validation failed: " + e.getMessage());
            }

            try {
                org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
                String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=" + geminiApiKey;

                String prompt = "Analyze the following startup idea and provide a marketScore (0-100 integer), competitionScore (0-100 integer), riskScore (0-100 integer), a verdict string (e.g. 'Highly Promising', 'Needs Improvement', 'Invalid Idea'), and a concise feedback string explaining the analysis (STRICT LIMIT: exactly 50 to 60 words). " +
                        "CRITICAL INSTRUCTION: First, carefully evaluate if the Idea Title, Problem, or Solution are just random gibberish, meaningless keyboard smashes (e.g., 'asdfg'), completely nonsensical, or lack any real meaning. If they are meaningless gibberish, you MUST return marketScore: 0, competitionScore: 0, riskScore: 100, verdict: 'Invalid Idea', and feedback explaining that the input appears to be meaningless and cannot be evaluated as a real startup idea. " +
                        "Idea Title: " + idea.getTitle() + ", Industry: " + idea.getIndustry() + ", Problem: " + idea.getProblem() + ", Solution: " + idea.getSolution() + ". " +
                        "Return ONLY a raw JSON object with keys: marketScore, competitionScore, riskScore, verdict, feedback. No markdown formatting, no code blocks.";

                // Escape quotes and newlines for JSON
                String safePrompt = prompt.replace("\"", "\\\"").replace("\n", " ").replace("\r", "");
                String requestBody = "{ \"contents\": [{ \"parts\":[{\"text\": \"" + safePrompt + "\"}] }] }";
                
                org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
                headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(requestBody, headers);

                org.springframework.http.ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
                
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(response.getBody());
                String textResponse = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
                
                textResponse = textResponse.replace("```json", "").replace("```", "").trim();
                
                return mapper.readValue(textResponse, ValidationResponse.class);
            } catch (Exception e) {
                System.err.println("Gemini API call failed: " + e.getMessage());
                // Fallback to mock logic below
            }
        }

        // Simulate AI processing time for the mock logic
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int marketScore =
                marketAnalysisService
                        .calculateMarketScore(
                                idea.getIndustry());

        int competitionScore =
                competitionAnalysisService
                        .calculateCompetitionScore(
                                idea.getIndustry());

        int riskScore =
                riskAnalysisService
                        .calculateRiskScore(
                                idea.getIndustry());

        String verdict;
        String feedback;

        if (marketScore >= 80) {
            verdict = "Highly Promising";
            feedback = "The market potential is excellent and the proposed solution effectively addresses the problem. There is strong growth potential with low to moderate risks.";
        } else if (marketScore >= 60) {
            verdict = "Promising Startup Idea";
            feedback = "The idea has potential, but the market is competitive. Focusing on a unique value proposition and mitigating execution risks will be key to success.";
        } else {
            verdict = "Needs Improvement";
            feedback = "The market potential appears limited or the competition is very high. Consider pivoting the idea or identifying a more specific niche before proceeding.";
        }

        return new ValidationResponse(
                marketScore,
                competitionScore,
                riskScore,
                verdict,
                feedback
        );
    }
    public DashboardStatsResponse getStats() {

        long totalIdeas = startupIdeaRepository.count();

        long aiIdeas =
                startupIdeaRepository.countByIndustry("AI");

        long edTechIdeas =
                startupIdeaRepository.countByIndustry("EdTech");

        return new DashboardStatsResponse(
                totalIdeas,
                aiIdeas,
                edTechIdeas
        );
    }

    public FeedResponse getExploreFeed(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StartupIdea> ideaPage;
        if (search != null && !search.trim().isEmpty()) {
            ideaPage = startupIdeaRepository.findByTitleContainingIgnoreCaseAndAuthorIsNotNullOrderByCreatedAtDesc(search.trim(), pageable);
        } else {
            ideaPage = startupIdeaRepository.findByAuthorIsNotNullOrderByCreatedAtDesc(pageable);
        }
        return convertToFeedResponse(ideaPage);
    }

    public FeedResponse getFollowingFeed(int page, int size) {
        User currentUser = getCurrentUser();
        Pageable pageable = PageRequest.of(page, size);
        Page<StartupIdea> ideaPage = startupIdeaRepository.findByAuthorInOrderByCreatedAtDesc(currentUser.getFollowing(), pageable);
        return convertToFeedResponse(ideaPage);
    }

    private FeedResponse convertToFeedResponse(Page<StartupIdea> ideaPage) {
        List<StartupIdeaResponse> dtoList = ideaPage.getContent().stream()
                .map(idea -> new StartupIdeaResponse(
                        idea.getId(),
                        idea.getTitle(),
                        idea.getIndustry(),
                        idea.getProblem(),
                        idea.getSolution(),
                        idea.getCreatedAt(),
                        idea.getAuthor() != null ? idea.getAuthor().getId() : null,
                        idea.getAuthor() != null ? idea.getAuthor().getName() : "Unknown",
                        ideaInteractionService.getAverageRating(idea.getId())
                ))
                .collect(Collectors.toList());
        return new FeedResponse(dtoList, ideaPage.getTotalPages(), ideaPage.getTotalElements());
    }
}

