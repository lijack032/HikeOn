package frontend.controller;

import java.util.List;

import backend.service.ChatbotService;
import backend.service.LocationService;
import backend.service.WeatherService;
import frontend.model.Location;
import frontend.model.Weather;
import frontend.view.panels.HomePanel;

/**
 * MainController class to handle the main operations of the application.
 * 
 * @null This class does not accept null values for its dependencies.
 */
public class MainController {
    private final HomePanel homePanel;
    private final WeatherService weatherService;
    private final LocationService locationService;
    private final ChatbotService chatbotService;

    public MainController(HomePanel homePanel, WeatherService weatherService, 
                          LocationService locationService, ChatbotService chatbotService) {
        this.homePanel = homePanel;
        this.weatherService = weatherService;
        this.locationService = locationService;
        this.chatbotService = chatbotService;

        // Initialize the weather display when the app starts
        updateWeatherData();
    }

    /**
     * Handles search requests from the user.
     *
     * @param query the search query entered by the user
     */
    public void handleSearchRequest(String query) {
        // Fetch locations based on the search query
        final List<Location> locations = locationService.searchLocations(query);
        homePanel.displayLocationResults(locations);
    }

    /**
     * Updates the weather data and displays it in the HomePanel's WeatherPanel.
     */
    public void updateWeatherData() {
        final Weather currentWeather = weatherService.getCurrentWeather();
        homePanel.updateWeatherPanel(currentWeather);
    }

    /**
     * Handles messages to/from the chatbot.
     *
     * @param userMessage the message entered by the user
     */
    public void handleChatbotRequest(String userMessage) {
        final String chatbotResponse = chatbotService.getChatbotResponse(userMessage);
        homePanel.getChatbotPanel().appendMessage("User: " + userMessage);
        homePanel.getChatbotPanel().appendMessage("AI: " + chatbotResponse);
    }

}
