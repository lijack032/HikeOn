package frontend.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.IOException;

import backend.service.WeatherService;
import entity.Weather;

/**
 * Controller for handling weather-related requests.
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
        String result;
        if (location == null || location.trim().isEmpty()) {
            result = "Location cannot be empty!";
        } else {
            try {
                // Fetch current weather and forecast data
                final Weather currentWeather = weatherService.getWeather(location);
                final List<Weather> forecast = weatherService.getWeatherWithForecast(location);

                // Format the data
                result = formatWeather(currentWeather, forecast);
            } catch (RuntimeException exception) {
                result = "Error fetching weather data: " + exception.getMessage();
            }
        }
        return result;
    }

    /**
     * Formats current weather and forecast into a user-friendly string.
     * @param currentWeather the current weather condition
     * @param forecast the list of Weather objects for the forecast
     * @return a formatted weather string
     */
    private String formatWeather(Weather currentWeather, List<Weather> forecast) {
        final StringBuilder formattedWeather = new StringBuilder();

        // Format current weather
        formattedWeather.append("Current Weather:\n");
        formattedWeather.append("- Condition: ").append(currentWeather.getCondition()).append("\n");
        formattedWeather.append("- Temperature: ").append(currentWeather.getTemperature()).append(" °C\n\n");

        // Format forecast
        formattedWeather.append("Forecast for the next 24 hours:\n");
        for (Weather weather : forecast) {
            final String time = formatTime(weather.getTimestamp());
            formattedWeather.append("- ").append(time).append(": ")
                    .append(weather.getCondition()).append(", ")
                    .append(weather.getTemperature()).append(" °C\n");
        }

        return formattedWeather.toString();
    }

    /**
     * Helper method to format time into a more readable format.
     * @param rawTime the raw timestamp (e.g., "2024-12-03 18:00:00")
     * @return formatted time as a string (e.g., "6:00 PM")
     */
    private String formatTime(String rawTime) {
        String formattedTime;
        try {
            final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final SimpleDateFormat outputFormat = new SimpleDateFormat("h:mm a");
            final Date date = inputFormat.parse(rawTime);
            formattedTime = outputFormat.format(date);
        } 
        catch (ParseException parseException) {
            parseException.printStackTrace();
            // Return raw time if formatting fails
            formattedTime = rawTime;
        }
        return formattedTime;
    }
}
