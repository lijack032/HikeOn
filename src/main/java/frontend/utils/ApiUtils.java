//package frontend.utils;
//
//import org.json.JSONObject;
//
//import frontend.model.Weather;
//import io.github.cdimascio.dotenv.Dotenv;
//
///**
// * Utility class for API operations.
// */
//public class ApiUtils {
//
//    private static final String WEATHER_API_KEY = Dotenv.load().get("OPENWEATHER_API_KEY");
//    private static final String WEATHER_API_BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
//    private static final String LOCATION_API_URL = "https://api.example.com/locations?query=";
//
//    /**
//     * Fetches the current weather for a given city.
//     *
//     * @param city The city for which to fetch weather data.
//     * @return Weather object containing the current weather details, or null if the response is empty or invalid.
//     * @throws IllegalArgumentException If the city name is null or empty.
//     */
//    public static Weather fetchCurrentWeather(String city) {
//        if (city == null || city.trim().isEmpty()) {
//            throw new IllegalArgumentException("City name cannot be null or empty.");
//        }
//
//        Weather weather = null;
//        try {
//            final String url = WEATHER_API_BASE_URL + "?q=" + city + "&appid=" + WEATHER_API_KEY + "&units=metric";
//
//            final String response = HttpClient.sendGetRequest(url);
//
//            if (!response.isEmpty()) {
//                final JSONObject jsonResponse = new JSONObject(response);
//                final String condition = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
//                final double temperature = jsonResponse.getJSONObject("main").getDouble("temp");
//                final double humidity = jsonResponse.getJSONObject("main").getDouble("humidity");
//                final double windSpeed = jsonResponse.getJSONObject("wind").getDouble("speed");
//                weather = new Weather(condition, temperature, humidity, windSpeed);
//            }
//        }
//        catch (IllegalArgumentException exception) {
//            System.err.println("Error fetching weather data for city: " + city + ". " + exception.getMessage());
//        }
//        return weather;
//    }
//
//}
