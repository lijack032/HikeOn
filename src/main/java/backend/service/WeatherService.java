package backend.service;

import frontend.model.Weather;
import frontend.utils.APIUtils;

/**
 * Service class for managing and fetching weather data.
 * This class interacts with the API utility to retrieve weather data
 * and provides it to other components of the application.
 */
public class WeatherService {
    private Weather currentWeather;

    /**
     * Constructs a WeatherService instance with no initial weather data.
     * The weather data can be updated by calling {@link #updateWeather()}.
     */
    public WeatherService() {
        this.currentWeather = null;
    }

    /**
     * Updates the current weather data by fetching the latest information
     * from the weather API. If the API call fails or returns null, a default
     * fallback weather object is set to avoid null pointer issues.
     */
    public void updateWeather() {
        try {
            currentWeather = APIUtils.fetchCurrentWeather();
            if (currentWeather == null) {
                // Fallback or default weather data
                currentWeather = new Weather("Unknown", 0.0, 0.0, "N/A");
            }
        } catch (Exception e) {
            // Log and handle gracefully
            System.err.println("Error fetching weather: " + e.getMessage());
            currentWeather = new Weather("Unknown", 0.0, 0.0, "N/A");
        }
    }

    /**
     * Retrieves the current weather data. If the weather data is outdated,
     * this method will automatically call {@link #updateWeather()} to fetch
     * the latest weather data before returning it.
     *
     * @return the current Weather object, or a default fallback object if
     *         the weather data could not be retrieved.
     */
    public Weather getCurrentWeather() {
        updateWeather();
        return currentWeather;
    }
}
