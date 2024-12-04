package backend.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Weather;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Service for fetching weather data.
 */
public class WeatherService {
    private static final String API_KEY = loadWeatherApiKey();
    private static final String WEATHER_API_URL = 
        "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s";
    private static final String FORECAST_API_URL = 
        "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&appid=%s";

    /**
     * Fetches the current weather data for a given location.
     * 
     * @param location the location to fetch weather for
     * @return a Weather object representing the current weather
     */
    public Weather getWeather(String location) {
        try {
            final String requestUrl = String.format(WEATHER_API_URL, location, API_KEY);
            final JSONObject weatherData = fetchWeatherData(requestUrl);

            // Parse and return current weather
            final String condition = weatherData.getJSONArray("weather").getJSONObject(0).getString("description");
            final double temperature = weatherData.getJSONObject("main").getDouble("temp");
            return new Weather(condition, temperature);
        } 
        catch (IOException e) {
            throw new RuntimeException("Error fetching current weather: " + e.getMessage(), e);
        }
    }

    /**
     * Fetches the weather forecast data for a given location.
     * 
     * @param location the location to fetch forecast for
     * @return a list of Weather objects representing the forecast
     */
    public List<Weather> getWeatherWithForecast(String location) {
        final List<Weather> forecastList = new ArrayList<>();
        try {
            final String requestUrl = String.format(FORECAST_API_URL, location, API_KEY);
            final JSONObject forecastData = fetchWeatherData(requestUrl);
            final JSONArray forecastArray = forecastData.getJSONArray("list");

            // Process the next 8 forecast intervals (e.g., next 24 hours if 3-hour intervals)
            for (int i = 0; i < 8; i++) {
                final JSONObject forecastEntry = forecastArray.getJSONObject(i);
                final long timestamp = forecastEntry.getLong("dt");
                final String formattedTime = convertUnixToTimestamp(timestamp);

                final String condition = forecastEntry.getJSONArray("weather").getJSONObject(0).getString("description");
                final double temperature = forecastEntry.getJSONObject("main").getDouble("temp");

                forecastList.add(new Weather(formattedTime, condition, temperature));
            }
        } 
        catch (IOException e) {
            throw new RuntimeException("Error fetching forecast data: " + e.getMessage(), e);
        }
        return forecastList;
    }

    /**
     * Converts a Unix timestamp to a formatted timestamp string.
     * 
     * @param unixTimestamp the Unix timestamp to convert
     * @return the formatted timestamp string (e.g., "2024-12-03 18:00:00")
     */
    private String convertUnixToTimestamp(long unixTimestamp) {
        final Date date = new Date(unixTimestamp * 1000); // Convert seconds to milliseconds
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private static String loadWeatherApiKey() {
        final Dotenv dotenv = Dotenv.configure()
            .filename("OpenWeatherKey.env")
            .directory("/Users/vabku/OneDrive/Desktop/CSC207/HikeOn/HikeOn")
            .load();
        final String apiKey = dotenv.get("OPENWEATHER_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("Weather API Key not found in Weather_key.env file");
        }
        return apiKey;
    }

    /**
     * Helper method to fetch JSON data from the OpenWeather API.
     * 
     * @param requestUrl the API request URL
     * @return the response as a JSONObject
     * @throws IOException if the request fails
     */
    private JSONObject fetchWeatherData(String requestUrl) throws IOException {
        final URI uri = URI.create(requestUrl);
        final HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if (connection.getResponseCode() != 200) {
            throw new IOException("Failed to fetch data: HTTP " + connection.getResponseCode());
        }

        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            final StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            return new JSONObject(response.toString());
        }
    }
}
