package frontend.controller;

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

    public WeatherController(WeatherPanel weatherPanel, WeatherService weatherService) {
        this.weatherPanel = weatherPanel;
        this.weatherService = weatherService;

        // Update weather data when the controller is initialized
        updateWeather();
    }

    /**
     * Updates the weather data by fetching the latest information from the weather service
     * and updating the weather panel.
     */
    public void updateWeather() {
        // Fetch the latest weather data
        final Weather weather = weatherService.getCurrentWeather();
        // Update the WeatherPanel with the fetched data
        weatherPanel.updateWeather(weather);
    }
}
