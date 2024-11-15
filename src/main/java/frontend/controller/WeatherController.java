package frontend.controller;

import frontend.view.panels.WeatherPanel;
import backend.service.WeatherService;
import frontend.model.Weather;

public class WeatherController {
    private final WeatherPanel weatherPanel;
    private final WeatherService weatherService;

    public WeatherController(WeatherPanel weatherPanel, WeatherService weatherService) {
        this.weatherPanel = weatherPanel;
        this.weatherService = weatherService;

        // Update weather data when the controller is initialized
        updateWeather();
    }

    public void updateWeather() {
        // Fetch the latest weather data
        Weather weather = weatherService.getCurrentWeather();
        weatherPanel.updateWeather(weather); // Update the WeatherPanel with the fetched data
    }
}
