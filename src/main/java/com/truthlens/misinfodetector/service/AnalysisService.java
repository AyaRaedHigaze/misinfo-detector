package com.truthlens.misinfodetector.service;

import com.truthlens.misinfodetector.model.AnalysisRequest;
import com.truthlens.misinfodetector.model.AnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

    private final AIService aiService;

    @Autowired
    public AnalysisService(AIService aiService) {
        this.aiService = aiService;
    }

    public AnalysisResult analyzeText(AnalysisRequest request) {
        return aiService.analyzeText(request.getText());
    }
}
