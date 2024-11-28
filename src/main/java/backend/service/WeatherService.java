package backend.service;

import frontend.model.Weather;
import frontend.utils.ApiUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class responsible for fetching and managing weather data.
 * This service provides methods to retrieve the current weather and ensures
 * robust error handling for API interactions.
 */
public class WeatherService {
    private static final String API_KEY = "7c49878c18fe506669243c238670b9ff\n";  // Replace with your actual API key
    private static final String FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast";


    private final OkHttpClient client = new OkHttpClient();

    // Method to get a 3-day weather forecast
    public String getWeather(String location) throws IOException {
        String url = FORECAST_URL + "?q=" + location + "&appid=" + API_KEY + "&units=metric";
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JSONObject jsonResponse = new JSONObject(response.body().string());
                return parseThreeDayForecast(jsonResponse);
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    // Method to parse and generate a 3-day weather forecast summary
    private String parseThreeDayForecast(JSONObject jsonResponse) {
        JSONArray forecastList = jsonResponse.getJSONArray("list");

        Map<String, DayForecast> dailyForecast = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < forecastList.length(); i++) {
            JSONObject forecast = forecastList.getJSONObject(i);
            String date = forecast.getString("dt_txt").split(" ")[0];

            double temp = forecast.getJSONObject("main").getDouble("temp");
            String condition = forecast.getJSONArray("weather").getJSONObject(0).getString("main");

            dailyForecast.putIfAbsent(date, new DayForecast());
            dailyForecast.get(date).addEntry(temp, condition);
        }

        StringBuilder forecastSummary = new StringBuilder("3-Day Forecast:\n");
        LocalDate today = LocalDate.now();
        for (int day = 1; day <= 3; day++) {
            String dateKey = today.plusDays(day).format(formatter);
            DayForecast dayForecast = dailyForecast.get(dateKey);

            if (dayForecast != null) {
                forecastSummary.append(dateKey).append(": ")
                        .append(dayForecast.getAverageTemp()).append("°C, ")
                        .append(dayForecast.getMostFrequentCondition()).append("\n");
            } else {
                forecastSummary.append(dateKey).append(": No data available\n");
            }
        }
        return forecastSummary.toString();
    }

    // Helper class for daily forecast data
    private static class DayForecast {
        private double tempSum = 0;
        private int count = 0;
        private Map<String, Integer> conditionFrequency = new HashMap<>();

        void addEntry(double temp, String condition) {
            tempSum += temp;
            count++;
            conditionFrequency.put(condition, conditionFrequency.getOrDefault(condition, 0) + 1);
        }

        double getAverageTemp() {
            return count > 0 ? Math.round((tempSum / count) * 10.0) / 10.0 : 0.0;
        }

        String getMostFrequentCondition() {
            return conditionFrequency.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("N/A");
        }
    }
}
