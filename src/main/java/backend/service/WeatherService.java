package backend.service;

import java.io.IOException;

import frontend.model.Weather;
import frontend.utils.APIUtils;

/**
 * Service class responsible for fetching and managing weather data.
 * This service provides methods to retrieve the current weather and ensures
 * robust error handling for API interactions.
 */
public class WeatherService {
    private Weather currentWeather;

    /**
     * Constructs a WeatherService instance with no initial weather data.
     * The weather data must be explicitly updated using updateWeather().
     */
    public WeatherService() {
        this.currentWeather = null;
    }

    /**
     * Fetches the latest weather data from the API and updates the internal state.
     * In case of errors or an invalid response, a default fallback Weather object
     * is created to ensure continuity.
     * Error Handling:
     * - Logs errors to the console in case of exceptions during the API call.
     * - Uses a default Weather object when the API returns null or fails.
     */
    public void updateWeather() {
        try {
            final Weather fetchedWeather = APIUtils.fetchCurrentWeather();
            if (fetchedWeather == null) {
                handleFallbackWeather("API returned null data.");
            }
            else {
                currentWeather = fetchedWeather;
            }
        }
        catch (IOException exception) {
            handleFallbackWeather("Error during API call: " + exception.getMessage());
        }
        catch (IllegalStateException exception) {
            handleFallbackWeather("Unexpected state: " + exception.getMessage());
        }
    }

    /**
     * Returns the current weather data. Automatically updates the data if it is outdated
     * or unavailable. If the update fails, a default fallback Weather object is returned.
     *
     * @return the current Weather object, guaranteed to be non-null.
     */
    public Weather getCurrentWeather() {
        if (currentWeather == null) {
            updateWeather();
        }
        return currentWeather;
    }

    /**
     * Handles fallback behavior by creating a default Weather object and logging a message.
     *
     * @param message the reason for falling back to the default weather data.
     */
    private void handleFallbackWeather(String message) {
        currentWeather = new Weather("Unknown", 0.0, 0.0, 0.0);
        System.err.println("Fallback triggered: " + message);
    }
}

