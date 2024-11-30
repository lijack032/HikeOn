package backend.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Service class responsible for fetching and managing weather data.
 * This service provides methods to retrieve the current weather and ensures
 * robust error handling for API interactions.
 */
public class WeatherService {
    private static final String API_KEY = "";
    private static final String FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast";

    private final OkHttpClient client = new OkHttpClient();

    // Method to get a 3-day weather forecast
    /**
     * Retrieves a 3-day weather forecast for the specified location.
     *
     * @param location the location for which to get the weather forecast
     * @return a string containing the 3-day weather forecast summary
     * @throws IOException if an I/O error occurs when calling the weather API
     */
    public String getWeather(String location) throws IOException {
        final String url = FORECAST_URL + "?q=" + location + "&appid=" + API_KEY + "&units=metric";
        final Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                final JSONObject jsonResponse = new JSONObject(response.body().string());
                return parseThreeDayForecast(jsonResponse);
            } 
            else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    private String parseThreeDayForecast(JSONObject jsonResponse) {
        final JSONArray forecastList = jsonResponse.getJSONArray("list");

        final Map<String, DayForecast> dailyForecast = new HashMap<>();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < forecastList.length(); i++) {
            final JSONObject forecast = forecastList.getJSONObject(i);
            final String date = forecast.getString("dt_txt").split(" ")[0];

            final double temp = forecast.getJSONObject("main").getDouble("temp");
            final String condition = forecast.getJSONArray("weather").getJSONObject(0).getString("main");

            dailyForecast.putIfAbsent(date, new DayForecast());
            dailyForecast.get(date).addEntry(temp, condition);
        }

        final StringBuilder forecastSummary = new StringBuilder("3-Day Forecast:\n");
        final LocalDate today = LocalDate.now();
        final int forecastDays = 3;
        for (int day = 1; day <= forecastDays; day++) {
            final String dateKey = today.plusDays(day).format(formatter);
            final DayForecast dayForecast = dailyForecast.get(dateKey);

            if (dayForecast != null) {
                forecastSummary.append(dateKey).append(": ")
                        .append(dayForecast.getAverageTemp()).append(" degrees C, ")
                        .append(dayForecast.getMostFrequentCondition()).append("\n");
            } 
            else {
                forecastSummary.append(dateKey).append(": No data available\n");
            }
        }
        return forecastSummary.toString();
    }

    /**
     * Helper class for daily forecast data.
     * @null
     */
    private static final class DayForecast {
        private double tempSum;
        private int count;
        private Map<String, Integer> conditionFrequency = new HashMap<>();

        void addEntry(double temp, String condition) {
            tempSum += temp;
            count++;
            conditionFrequency.put(condition, conditionFrequency.getOrDefault(condition, 0) + 1);
        }

        double getAverageTemp() {
            final double roundingFactor = 10.0;
            double averageTemp = 0.0;
            if (count > 0) {
                averageTemp = Math.round((tempSum / count) * roundingFactor) / roundingFactor;
            }
            return averageTemp;
        }

        String getMostFrequentCondition() {
            return conditionFrequency.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("N/A");
        }
    }
}
