package frontend.utils;

import frontend.model.Weather;
import frontend.model.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APIUtils {

    private static final String WEATHER_API_KEY = "your-weather-api-key";
    private static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather?q=Toronto&appid=" + WEATHER_API_KEY + "&units=metric";
    private static final String LOCATION_API_URL = "https://api.example.com/locations?query=";

    // Fetch current weather data
    public static Weather fetchCurrentWeather() {
        String response = HTTPClient.sendGetRequest(WEATHER_API_URL);

        if (response != null && !response.isEmpty()) {
            JSONObject jsonResponse = new JSONObject(response);
            String condition = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
            int temperature = jsonResponse.getJSONObject("main").getInt("temp");
            return new Weather(condition, temperature);
        }
        return null;
    }

    // Fetch nearby locations based on the user's search query
    public static List<Location> fetchNearbyLocations(String query) {
        String response = HTTPClient.sendGetRequest(LOCATION_API_URL + query);

        List<Location> locations = new ArrayList<>();
        if (response != null && !response.isEmpty()) {
            JSONArray jsonResponse = new JSONArray(response);
            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject locationJson = jsonResponse.getJSONObject(i);
                String locationName = locationJson.getString("name");
                locations.add(new Location(locationName));
            }
        }
        return locations;
    }
}
