package com.truthlens.misinfodetector.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.truthlens.misinfodetector.model.AnalysisResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class AIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public AnalysisResult analyzeText(String inputText) {
        String endpoint = "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-pro:generateContent?key=" + apiKey;

        try {
            String prompt = "Claim: \"" + inputText + "\"\n\n" +
                    "Analyze the claim for:\n" +
                    "1. Logical fallacies (if any)\n" +
                    "2. Cognitive biases (if any)\n" +
                    "3. A scientific explanation to address or correct the claim, if needed.\n\n" +
                    "If the claim is purely subjective (such as an opinion or personal statement), and does not contain misinformation, logical fallacies, or cognitive biases, respond with an empty flags list and a simple explanation saying it's a personal opinion or neutral statement.\n\n" +
                    "Return the `sources` field as a list of plain URLs in string format only.\n\n" +
                    "Respond STRICTLY with a valid JSON object. Do NOT use Markdown formatting (no ```), and do NOT include any explanation or surrounding text. JSON only.\n\n" +
                    "Required format:\n" +
                    "{\n" +
                    "  \"flags\": [\"...\"],\n" +
                    "  \"explanation\": \"...\",\n" +
                    "  \"sources\": [\"...\"]\n" +
                    "}";

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root = mapper.createObjectNode();
            ArrayNode contents = mapper.createArrayNode();

            ObjectNode part = mapper.createObjectNode();
            part.put("text", prompt);

            ObjectNode inner = mapper.createObjectNode();
            inner.set("parts", mapper.createArrayNode().add(part));
            contents.add(inner);

            root.set("contents", contents);
            String requestBody = mapper.writeValueAsString(root);

            // إعداد الطلب
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // إرسال الطلب إلى Gemini
            ResponseEntity<String> response = restTemplate.postForEntity(endpoint, entity, String.class);

            // استخراج الرد من النموذج
            String modelReply = extractModelText(response.getBody());

            // تحويل الرد من JSON إلى AnalysisResult
            AnalysisResult result = mapper.readValue(modelReply, AnalysisResult.class);

            // ✅ تحديد ما إذا كان التحليل محايدًا (أي لا يحتوي على أي flags)
            result.setNeutral(result.getFlags() == null || result.getFlags().isEmpty());

            return result;

        } catch (Exception e) {
            AnalysisResult errorResult = new AnalysisResult();
            errorResult.setExplanation("Error analyzing text: " + e.getMessage());
            errorResult.setFlags(Collections.emptyList());
            errorResult.setSources(Collections.emptyList());
            errorResult.setNeutral(true);
            return errorResult;
        }
    }

    private String extractModelText(String responseJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseJson);
        String rawText = root.at("/candidates/0/content/parts/0/text").asText();

        // إزالة Markdown formatting لو وُجد
        if (rawText.startsWith("```")) {
            rawText = rawText.replaceAll("```json|```", "").trim();
        }

        return rawText;
    }
}
