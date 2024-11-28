package backend.service;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service for handling chatbot operations using OpenAI GPT-4o mini.
 * Manages session-based conversation history and integrates with OpenAI API.
 */
public class ChatbotService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions"; // Placeholder
    private static final String API_KEY = loadApiKey();

    private static String loadApiKey() {
        Dotenv dotenv = Dotenv.configure()
                            .directory("./") // Ensure it points to the correct path
                            .load();
        return dotenv.get("OPENAI_API_KEY");
    }


    // Map to store session IDs and their corresponding conversation history
    private final Map<String, List<String>> sessionHistories;

    public ChatbotService() {
        this.sessionHistories = new HashMap<>();
    }

    /**
     * Starts a new chatbot session and returns a unique session ID.
     *
     * @return A unique session ID.
     */
    public String startSession() {
        String sessionId = UUID.randomUUID().toString();
        sessionHistories.put(sessionId, new ArrayList<>());
        return sessionId;
    }

    /**
     * Handles user input, communicates with the OpenAI API, and returns the chatbot's response.
     *
     * @param sessionId   The unique session ID.
     * @param userMessage The message input by the user.
     * @return The chatbot's response.
     */
    public String getChatbotResponse(String sessionId, String userMessage) {
        // Ensure the session exists
        if (!sessionHistories.containsKey(sessionId)) {
            throw new IllegalArgumentException("Invalid session ID.");
        }

        // Add user message to the session's history
        sessionHistories.get(sessionId).add("User: " + userMessage);

        // Prepare the request payload
        String requestBody = """
                {
                    "model": "gpt-4-mini",
                    "messages": [
                        {"role": "system", "content": "You are a hiking and activity assistant."},
                        {"role": "user", "content": "%s"}
                    ]
                }
                """.formatted(userMessage);

        try {
            // Send API request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Parse and return response
            if (response.statusCode() == 200) {
                String chatbotResponse = extractResponse(response.body());
                sessionHistories.get(sessionId).add("AI: " + chatbotResponse);
                return chatbotResponse;
            } else {
                throw new RuntimeException("API call failed: " + response.body());
            }
        } catch (Exception e) {
            sessionHistories.get(sessionId).add("AI: Error occurred while processing your request.");
            return "An error occurred. Please try again later.";
        }
    }

    /**
     * Retrieves the conversation history for the given session ID.
     *
     * @param sessionId The session ID.
     * @return The conversation history.
     */
    public List<String> getConversationHistory(String sessionId) {
        return sessionHistories.getOrDefault(sessionId, Collections.emptyList());
    }

    /**
     * Extracts the chatbot's response from the API response body.
     *
     * @param responseBody The raw response body.
     * @return The chatbot's response text.
     */
    private String extractResponse(String responseBody) {
        // Use a JSON library like Jackson or Gson to parse the response
        // Assuming the response contains a field "choices" which is a list, and inside that "text"
        try {
            JsonNode rootNode = new ObjectMapper().readTree(responseBody);
            String chatbotResponse = rootNode.path("choices").get(0).path("message").path("content").asText();
            return chatbotResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response.";
        }
    }
}
