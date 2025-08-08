package com.truthlens.misinfodetector.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to analyze a text input.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisRequest {

    /**
     * The text or claim to be analyzed for misinformation, bias, or fallacies.
     */
    private String text;
}