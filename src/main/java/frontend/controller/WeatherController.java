package frontend.controller;

import backend.service.WeatherService;

/**
 * Controller for handling weather-related requests.
 * @null
 */
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController() {
        this.weatherService = new WeatherService();
    }

    /**
     * Fetches and returns formatted weather data for a given location.
     * @param location the location to fetch weather for
     * @return formatted weather data
     */
    public String getFormattedWeather(String location) {
        if (location == null || location.trim().isEmpty()) {
            return "Location cannot be empty!";
        }

        // Fetch weather data
        final String weatherData = weatherService.getWeather(location);

        // Optionally, add more formatting or validation here
        return weatherData;
    }
}
