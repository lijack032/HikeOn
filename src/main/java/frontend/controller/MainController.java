package frontend.controller;

import java.io.IOException;

import backend.service.ChatbotService;
import backend.service.WeatherService;
import frontend.model.Weather;
import frontend.view.panels.ChatbotPanel;
import frontend.view.panels.HomePanel;

/**
 * MainController class to handle the main operations of the application.
 *
 * @null This class does not accept null values for its dependencies.
 */
public class MainController {
    private final HomePanel homePanel;
    private final WeatherService weatherService;
    private final ChatbotService chatbotService;

    /**
     * Constructs the MainController with its dependencies and initializes
     * the weather display when the app starts.
     *
     * @param homePanel the main panel of the application
     * @param weatherService the service for fetching weather data
     * @param chatbotService the service for chatbot interactions
     * @throws IOException if there is an issue during initialization
     */
    public MainController(HomePanel homePanel, WeatherService weatherService, 
                          ChatbotService chatbotService) throws IOException {
        this.homePanel = homePanel;
        this.weatherService = weatherService;
        // this.locationService = locationService;
        this.chatbotService = chatbotService;
    }

    /**
     * Updates the weather data for the specified city and displays it in the HomePanel's WeatherPanel.
     *
     * @param city the name of the city for which to fetch and display weather data
     * @throws IOException if there is an issue during the weather data update
     * @throws IllegalArgumentException if the city name is wrongly entered
     */
    public void updateWeatherData(String city) throws IOException {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City name cannot be null or empty.");
        }
        final String currentWeather = weatherService.getWeather(city.trim());
        if (currentWeather != null) {
            final Weather weather = new Weather(currentWeather);
            homePanel.updateWeatherPanel(weather);
        }
    }

    /**
    * Handles messages to/from the chatbot.
    *
    * @param userMessage the message entered by the user
    */
    public void handleChatbotRequest(String userMessage) {
        // Ensure the user message is not null or empty
        if (userMessage == null || userMessage.trim().isEmpty()) {
            homePanel.getChatbotPanel().displayConversation("System: Please enter a valid message.\n");
        }   
        else {

            try {
                // Ensure a session ID is created for chatbot interactions
                final String sessionId = chatbotService.startSession();

                // Get the chatbot response
                final String chatbotResponse = chatbotService.getChatbotResponse(sessionId, userMessage.trim());

                // Append user and AI messages to the conversation area
                final ChatbotPanel chatbotPanel = homePanel.getChatbotPanel();
                chatbotPanel.displayConversation(
                    chatbotPanel.getUserInput() + "User: " + userMessage + "\nAI: " + chatbotResponse + "\n");

                // Clear the input field
                chatbotPanel.clearInputField();
            } 
            catch (IllegalArgumentException ex) {
                homePanel.getChatbotPanel().displayConversation("System: " + ex.getMessage() + "\n");
            }
        }
    }

    /**
    * Initializes the ChatbotPanel by adding a listener to handle send button clicks.
    */
    public void initializeChatbotPanel() {
        final ChatbotPanel chatbotPanel = homePanel.getChatbotPanel();

        // Add a listener to handle send button clicks
        chatbotPanel.addSendButtonListener(event -> {
            final String userInput = chatbotPanel.getUserInput();
            handleChatbotRequest(userInput);
        });
    }
}
