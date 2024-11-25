package frontend.utils;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import frontend.model.Location;
import frontend.model.Weather;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Utility class for API operations.
 * @null
 */
public class ApiUtils {

    private static final String WEATHER_API_KEY = Dotenv.load().get("OPENWEATHER_API_KEY");
    private static final String WEATHER_API_BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String LOCATION_API_URL = "https://api.example.com/locations?query=";

    /**
     * Fetches the current weather for a given city.
     *
     * @param city The city for which to fetch weather data.
     * @return Weather object containing the current weather details, or null if the response is empty or invalid.
     * @throws IllegalArgumentException If the city name is null or empty.
     */
    public static Weather fetchCurrentWeather(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City name cannot be null or empty.");
        }

        try {
            final String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
            final String url = WEATHER_API_BASE_URL + "?q=" + encodedCity + "&appid=" + WEATHER_API_KEY + "&units=metric";

            final String response = HttpClient.sendGetRequest(url);
            Weather weather = null;

            if (!response.isEmpty()) {
                final JSONObject jsonResponse = new JSONObject(response);
                final String condition = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
                final double temperature = jsonResponse.getJSONObject("main").getDouble("temp");
                final double humidity = jsonResponse.getJSONObject("main").getDouble("humidity");
                final double windSpeed = jsonResponse.getJSONObject("wind").getDouble("speed");
                weather = new Weather(condition, temperature, humidity, windSpeed);
            }
            return weather;

        }
        catch (UnsupportedEncodingException exception) {
            System.err.println("Encoding error for city: " + city + ". " + exception.getMessage());
            return null;
        }
        catch (IllegalArgumentException exception) {
            System.err.println("Error fetching weather data for city: " + city + ". " + exception.getMessage());
            return null;
        }

    }

    /**
     * Fetches nearby locations based on the user's search query.
     * @param query The search query for locations.
     * @return A list of Location objects based on the search query.
     */
    public static List<Location> fetchNearbyLocations(String query) {
        final String response = HttpClient.sendGetRequest(LOCATION_API_URL + query);

        final List<Location> locations = new ArrayList<>();
        if (response != null && !response.isEmpty()) {
            final JSONArray jsonResponse = new JSONArray(response);
            for (int i = 0; i < jsonResponse.length(); i++) {
                final JSONObject locationJson = jsonResponse.getJSONObject(i);
                final String locationName = locationJson.getString("name");
                locations.add(new Location(locationName));
            }
        }
        return locations;
    }
}
