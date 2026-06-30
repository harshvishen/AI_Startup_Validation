package com.utkarsh.startupvalidation.service;

import org.springframework.stereotype.Service;

@Service
public class MarketAnalysisService {

    public int calculateMarketScore(String industry) {

        if (industry == null) {
            return 50;
        }

        if (industry.equalsIgnoreCase("AI")) {
            return 90;
        }

        if (industry.equalsIgnoreCase("EdTech")) {
            return 80;
        }

        if (industry.equalsIgnoreCase("HealthTech")) {
            return 85;
        }

        return 60;
    }
}