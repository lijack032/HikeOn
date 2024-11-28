package backend.service;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service for handling chatbot operations using OpenAI GPT-4o mini.
 * Manages session-based conversation history and integrates with OpenAI API.
 */
public class ChatbotService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = loadApiKey();

    // HTTP status code for OK
    private static final int HTTP_STATUS_OK = 200;

    // Map to store session IDs and their corresponding conversation history
    private final Map<String, List<String>> sessionHistories;

    public ChatbotService() {
        this.sessionHistories = new HashMap<>();
    }

    private static String loadApiKey() {
        final Dotenv dotenv = Dotenv.configure()
                            .directory("./")
                            .load();
        return dotenv.get("OPENAI_API_KEY");
    }

    /**
     * Starts a new chatbot session and returns a unique session ID.
     *
     * @return A unique session ID.
     */
    public String startSession() {
        final String sessionId = UUID.randomUUID().toString();
        sessionHistories.put(sessionId, new ArrayList<>());
        return sessionId;
    }

    /**
     * Handles user input, communicates with the OpenAI API, and returns the chatbot's response.
     *
     * @param sessionId   The unique session ID.
     * @param userMessage The message input by the user.
     * @return The chatbot's response.
     * @throws IllegalArgumentException if the session ID is invalid.
     */
    public String getChatbotResponse(String sessionId, String userMessage) {
        // Ensure the session exists
        if (!sessionHistories.containsKey(sessionId)) {
            throw new IllegalArgumentException("Invalid session ID.");
        }

        // Add user message to the session's history
        sessionHistories.get(sessionId).add("User: " + userMessage);

        // Prepare the request payload
        final String requestBody = """
                {
                    "model": "gpt-4-mini",
                    "messages": [
                        {"role": "system", "content": "You are a hiking and activity assistant."},
                        {"role": "user", "content": "%s"}
                    ]
                }
                """.formatted(userMessage);

        String chatbotResponse;
        try {
            // Send API request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            final HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HTTP_STATUS_OK) {
                chatbotResponse = extractResponse(response.body());
                // Parse and return response
                if (response.statusCode() == 200) {
                    chatbotResponse = extractResponse(response.body());
                    sessionHistories.get(sessionId).add("AI: " + chatbotResponse);
                } else {
                    throw new RuntimeException("API call failed: " + response.body());
                }
            } 
            else {
                throw new RuntimeException("API call failed: " + response.body());
            }
        }
        catch (Exception e) {
            sessionHistories.get(sessionId).add("AI: Error occurred while processing your request.");
            chatbotResponse = "An error occurred. Please try again later.";
        }
        return chatbotResponse;
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
        String chatbotResponse;
        try {
            final JsonNode rootNode = new ObjectMapper().readTree(responseBody);
            chatbotResponse = rootNode.path("choices").get(0).path("message").path("content").asText();
        } 
        catch (IOException ioException) {
            ioException.printStackTrace();
            chatbotResponse = "Error parsing response.";
        }
        return chatbotResponse;
    }
}
