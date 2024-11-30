package backend.service;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatbotService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = loadApiKey();
    private static final int HTTP_STATUS_OK = 200;

    // Thread-safe session management
    private final Map<String, List<String>> sessionHistories = new ConcurrentHashMap<>();

    public ChatbotService() {}

    private static String loadApiKey() {
        final Dotenv dotenv = Dotenv.configure()
                                    .filename("OpenAI.env") // Specify custom file name
                                    .directory("C:/Users/Arshiya/Desktop/Projects/HikeOn")
                                    .load();
        String apiKey = dotenv.get("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("API Key not found in OpenAI.env file");
        }
        return apiKey;
    }
    

    public String startSession() {
        final String sessionId = UUID.randomUUID().toString();
        sessionHistories.put(sessionId, new ArrayList<>());
        return sessionId;
    }

    public String getChatbotResponse(String sessionId, String userMessage) {
        if (!sessionHistories.containsKey(sessionId)) {
            throw new IllegalArgumentException("Invalid session ID.");
        }

        sessionHistories.get(sessionId).add("User: " + userMessage);

        final String requestBody = """
                {
                    "model": "gpt-4-mini",
                    "messages": [
                        {"role": "system", "content": "You are a hiking and activity assistant."},
                        {"role": "user", "content": "%s"}
                    ]
                }
                """.formatted(userMessage);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HTTP_STATUS_OK) {
                String chatbotResponse = extractResponse(response.body());
                sessionHistories.get(sessionId).add("AI: " + chatbotResponse);
                return chatbotResponse;
            } else {
                throw new RuntimeException("API call failed with status " + response.statusCode() + ": " + response.body());
            }
        } catch (Exception e) {
            sessionHistories.get(sessionId).add("AI: Error occurred while processing your request.");
            return "An error occurred. Please try again later.";
        }
    }

    public List<String> getConversationHistory(String sessionId) {
        return sessionHistories.getOrDefault(sessionId, Collections.emptyList());
    }

    private String extractResponse(String responseBody) {
        try {
            JsonNode rootNode = new ObjectMapper().readTree(responseBody);
            JsonNode choicesNode = rootNode.path("choices");
            if (choicesNode.isArray() && choicesNode.size() > 0) {
                return choicesNode.get(0).path("message").path("content").asText();
            } else {
                throw new IOException("Invalid JSON response structure.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error parsing response.";
        }
    }
}
