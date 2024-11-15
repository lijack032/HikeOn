package frontend.controller;

import frontend.model.Location;
import frontend.model.Weather;
import frontend.view.panels.HomePanel;
import backend.service.Location;
import backend.service.WeatherService;
import backend.service.ChatbotService;

import java.util.List;

public class MainController {
    private final HomePanel homePanel;
    private final WeatherService weatherService;
    private final LocationService locationService;
    private final ChatbotService chatbotService;

    public MainController(HomePanel homePanel, WeatherService weatherService, LocationService locationService, ChatbotService chatbotService) {
        this.homePanel = homePanel;
        this.weatherService = weatherService;
        this.locationService = locationService;
        this.chatbotService = chatbotService;

        // Initialize the weather display when the app starts
        updateWeatherData();
    }

    // Method to handle search requests from the user
    public void handleSearchRequest(String query) {
        // Fetch locations based on the search query
        List<Location> locations = locationService.searchLocations(query);
        homePanel.displayLocationResults(locations);
    }

    // Method to update weather data and display it in the HomePanel's WeatherPanel
    public void updateWeatherData() {
        Weather currentWeather = weatherService.getCurrentWeather();
        homePanel.updateWeatherPanel(currentWeather);
    }

    // Method to handle messages to/from the chatbot
    public void handleChatbotRequest(String userMessage) {
        String chatbotResponse = chatbotService.getChatbotResponse(userMessage);
        homePanel.getChatbotPanel().appendMessage("User: " + userMessage);
        homePanel.getChatbotPanel().appendMessage("AI: " + chatbotResponse);
    }

    // Additional methods could be added for other interactions as needed
}
