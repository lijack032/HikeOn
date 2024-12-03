package backend.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.json.JSONObject;

/**
 * Service for fetching weather data.
 */
public class WeatherService {
    private static final String API_KEY = "ab2557a806139364c7c42ed9b554d1f4";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    /**
     * Fetches the weather data for a given location.
     * @param location the location to fetch the weather for
     * @return a string describing the weather and temperature
     */
    public String getWeather(String location) {
        String result = "";
        try {
            final String requestUrl = String.format(API_URL, location, API_KEY);
            final URI uri = new URI(requestUrl);
            final URL url = uri.toURL();
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() != 200) {
                throw new IOException("Failed to fetch weather data. Response code: " + connection.getResponseCode());
            }

            final StringBuilder response = new StringBuilder();
            try (Scanner scanner = new Scanner(connection.getInputStream())) {
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
            }

            final JSONObject weatherData = new JSONObject(response.toString());
            final String description = weatherData.getJSONArray("weather").getJSONObject(0).getString("description");
            final double temperature = weatherData.getJSONObject("main").getDouble("temp");
            result = String.format("Weather: %s, Temperature: %.2f°C", description, temperature);
        } 
        catch (IOException | URISyntaxException e) {
            System.err.println("Exception: " + e.getMessage());
            result = "Unable to fetch weather data";
        }
        return result;
    }

    /**
     * Fetches the current weather and forecast for a given location.
     * @param location the location to fetch the weather and forecast for
     * @return a string describing the current weather and forecast
     */
    public String getWeatherWithForecast(String location) {
        StringBuilder result = new StringBuilder();
        try {
            final String currentWeatherUrl = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s", location, API_KEY);
            final String forecastUrl = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&appid=%s", location, API_KEY);
    
            // Fetch current weather
            JSONObject currentWeatherData = fetchWeatherData(currentWeatherUrl);
            String description = currentWeatherData.getJSONArray("weather").getJSONObject(0).getString("description");
            double temperature = currentWeatherData.getJSONObject("main").getDouble("temp");
            result.append(String.format("Current Weather: %s\nTemperature: %.1f°C\n", description, temperature));
    
            // Fetch forecast
            JSONObject forecastData = fetchWeatherData(forecastUrl);
            result.append("\nForecast:\n");
            for (int i = 0; i < 5; i++) { // Next 5 intervals (typically every 3 hours)
                JSONObject forecastEntry = forecastData.getJSONArray("list").getJSONObject(i);
                final String time = forecastEntry.getString("dt_txt");
                final String forecastDescription = forecastEntry.getJSONArray("weather")
                        .getJSONObject(0).getString("description");
                double forecastTemp = forecastEntry.getJSONObject("main").getDouble("temp");
                result.append(String.format("%s - %s, %.1f°C\n", time, forecastDescription, forecastTemp));
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            result.append("Unable to fetch weather data or forecast.");
        }
        return result.toString();
    }
    
    private JSONObject fetchWeatherData(String requestUrl) throws IOException {
        URI uri = URI.create(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
    
        if (connection.getResponseCode() != 200) {
            throw new IOException("Failed to fetch weather data: " + connection.getResponseCode());
        }
    
        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();
    
        return new JSONObject(response.toString());
    }
}
