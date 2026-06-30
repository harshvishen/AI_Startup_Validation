package com.utkarsh.startupvalidation.service;

import org.springframework.stereotype.Service;

@Service
public class RiskAnalysisService {

    public int calculateRiskScore(String industry) {

        if (industry == null) {
            return 50;
        }

        if (industry.equalsIgnoreCase("AI")) {
            return 40;
        }

        if (industry.equalsIgnoreCase("EdTech")) {
            return 35;
        }

        return 60;
    }
}