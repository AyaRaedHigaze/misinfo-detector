package com.truthlens.misinfodetector.service;

import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleFactCheckService {

    public List<String> generateSearchLinks(String claimText) {
        String encodedQuery = URLEncoder.encode(claimText, StandardCharsets.UTF_8);

        List<String> links = new ArrayList<>();
        links.add("https://en.wikipedia.org/wiki/Special:Search?search=" + encodedQuery);
        links.add("https://www.google.com/search?q=" + encodedQuery + "+site:factcheck.org");
        links.add("https://www.google.com/search?q=" + encodedQuery + "+site:snopes.com");
        links.add("https://www.google.com/search?q=" + encodedQuery + "+site:politifact.com");

        return links;
    }
}
