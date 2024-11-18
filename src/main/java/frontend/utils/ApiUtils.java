package frontend.utils;

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

    private static final Dotenv DOTENV = Dotenv.load();

    private static final String LOCATION_API_URL = "https://api.example.com/locations?query=";

    private static final String WEATHER_API_KEY = DOTENV.get("OPENWEATHER_API_KEY");

    private static final String WEATHER_API_URL = "https://openweathermap.org/api" 
            + WEATHER_API_KEY + "&units=metric";

    private static final String MAIN_KEY = "main";

    /**
     * Fetches the current weather for Toronto.
     * @return Weather object containing the current weather details or null if the response is empty.
     */
    public static Weather fetchCurrentWeather() {
        final String response = HttpClient.sendGetRequest(WEATHER_API_URL);
        Weather weather = null;

        if (response != null && !response.isEmpty()) {
            final JSONObject jsonResponse = new JSONObject(response);
            final String condition = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
            final double temperature = jsonResponse.getJSONObject(MAIN_KEY).getDouble("temp");
            final double humidity = jsonResponse.getJSONObject(MAIN_KEY).getDouble("humidity");
            final double windSpeed = jsonResponse.getJSONObject(MAIN_KEY).getDouble("Wind Speed");
            weather = new Weather(condition, temperature, humidity, windSpeed);
        }
        return weather;
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