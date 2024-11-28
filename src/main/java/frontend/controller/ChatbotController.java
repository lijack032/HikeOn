package frontend.controller;

import backend.service.ChatbotService;
import frontend.model.ChatbotSession;
import frontend.view.panels.ChatbotPanel;

/**
 * Controller for handling chatbot interactions.
 * Manages user messages, chatbot responses, and stores conversation history.
 */
public class ChatbotController {
    private final ChatbotPanel chatbotPanel;
    private final ChatbotService chatbotService;
    private ChatbotSession currentSession;

    public ChatbotController(ChatbotPanel chatbotPanel, ChatbotService chatbotService) {
        this.chatbotPanel = chatbotPanel;
        this.chatbotService = chatbotService;
    }

    /**
     * Starts a new chatbot session or retrieves an existing one based on the session ID.
     * 
     * @param sessionId unique identifier for the session
     */
    public void startChatSession(String sessionId) {
        currentSession = new ChatbotSession(sessionId); // Initialize a new session
    }

    /**
     * Handles the user's message to the chatbot.
     * Sends the message to the chatbot service and updates the conversation history.
     *
     * @param userMessage the message sent by the user
     */
    public void handleUserMessage(String userMessage) {
        // Add the user message to the session's conversation history
        currentSession.addMessage("User: " + userMessage);
        
        // Get the response from the chatbot service
        String chatbotResponse = chatbotService.getChatbotResponse(userMessage, currentSession.getSessionId());
        
        // Add the chatbot's response to the session's conversation history
        currentSession.addMessage("AI: " + chatbotResponse);
        
        // Display the updated conversation on the ChatbotPanel
        String conversationText = String.join("\n", currentSession.getConversationHistory());
        chatbotPanel.displayConversation(conversationText);
    }

    /**
     * Handles the scenario when the user closes the chat or finishes the session.
     */
    public void endChatSession() {
        // Optionally, you can clear the session history when the chat ends
        currentSession.clearHistory();
    }
}
