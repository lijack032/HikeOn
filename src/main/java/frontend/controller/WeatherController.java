package frontend.controller;

import java.io.IOException;

import backend.service.WeatherService;
import frontend.model.Weather;
import frontend.view.panels.WeatherPanel;

/**
 * Controller for managing weather data and updating the weather panel.
 *
 * @null This class does not accept null values for its dependencies.
 */
public class WeatherController {
    private final WeatherPanel weatherPanel;
    private final WeatherService weatherService;

    public WeatherController(WeatherPanel weatherPanel, WeatherService weatherService) throws IOException {
        this.weatherPanel = weatherPanel;
        this.weatherService = weatherService;

    }

    /**
     * Updates the weather data by fetching the latest information from the weather service
     * and updating the weather panel.
     * @param city is the location that the user wants to check the weather of.
     */
    public void updateWeather(String city) {
        // Fetch the latest weather data
        final Weather weather = weatherService.getCurrentWeather(city);
        // Update the WeatherPanel with the fetched data
        weatherPanel.updateWeather(weather);
    }
}
