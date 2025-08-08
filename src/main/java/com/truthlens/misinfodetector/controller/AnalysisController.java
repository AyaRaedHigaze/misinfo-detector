package com.truthlens.misinfodetector.controller;

import com.truthlens.misinfodetector.model.AnalysisRequest;
import com.truthlens.misinfodetector.model.AnalysisResult;
import com.truthlens.misinfodetector.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AnalysisController {

    private final AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResult> analyze(@RequestBody AnalysisRequest request) {
        AnalysisResult result = analysisService.analyzeText(request);
        return ResponseEntity.ok(result);
    }
}