package use_case;

import frontend.controller.WeatherController;

/**
 * Interactor for managing weather-related use cases.
 */
public class WeatherInteractor {
    private final WeatherController weatherController;

    /**
     * Constructs a WeatherInteractor with the given WeatherController.
     * 
     * @param weatherController the controller to handle weather-related operations
     */
    public WeatherInteractor(WeatherController weatherController) {
        this.weatherController = weatherController;
    }

    /**
     * Handles the use case where a user enters a location to fetch weather and forecast data.
     * 
     * @param location the location entered by the user
     * @return formatted weather and forecast data
     * @throws IllegalArgumentException if the location is null or empty
     */
    public String fetchWeatherForLocation(String location) {
        // Validate location
        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty or null");
        }

        // Delegate the request to the controller and return the result
        return weatherController.getFormattedWeather(location);
    }
}
