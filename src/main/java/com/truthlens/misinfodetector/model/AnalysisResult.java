package com.truthlens.misinfodetector.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResult {

    private List<String> flags;
    private String explanation;
    private List<String> sources;

    private boolean neutral;
}