package backend.controller;

import backend.service.WeatherService;
import frontend.model.Weather;

/**
 * Backend WeatherController for managing weather-related operations.
 */
public class WeatherController {
    private final WeatherService weatherService;

    /**
     * Constructs a WeatherController with a given WeatherService.
     *
     * @param weatherService the WeatherService to fetch weather data
     */
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Retrieves the current weather data.
     *
     * @return the current Weather object
     */
    public Weather getCurrentWeather() {
        return weatherService.getCurrentWeather();
    }

    /**
     * Updates the weather data by calling the WeatherService to fetch the latest data.
     */
    public void updateWeather() {
        weatherService.updateWeather();
    }
}

