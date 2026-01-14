package com.example.sentimentapi;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SentimentController {

    @GetMapping("/sentiment")
    public Map<String, String> analyze(@RequestParam String text) {
        Map<String, String> response = new HashMap<>();
        String sentiment;
        
        if (text.toLowerCase().contains("good")) {
            sentiment = "positive";
        } else {
            sentiment = "neutral";
        }
        
        response.put("sentiment", sentiment);
        return response;
    }
}
