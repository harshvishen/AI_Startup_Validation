package com.utkarsh.startupvalidation.service;

import org.springframework.stereotype.Service;

@Service
public class CompetitionAnalysisService {

    public int calculateCompetitionScore(String industry) {

        if (industry == null) {
            return 50;
        }

        if (industry.equalsIgnoreCase("AI")) {
            return 60;
        }

        if (industry.equalsIgnoreCase("EdTech")) {
            return 70;
        }

        return 50;
    }
}