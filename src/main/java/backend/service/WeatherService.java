package backend.service;

import java.io.IOException;

import org.json.JSONObject;

import frontend.model.Weather;
import frontend.utils.APIUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherService {

    // Method to get a 3-day weather forecast
    public Weather getCurrentWeather() throws IOException {
        final String url = APIUtils.get_weather_API_URL();
        final Request request = new Request.Builder().url(url).build();
        final OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                final JSONObject jsonResponse = new JSONObject(response.body().string());
                return getForcast(jsonResponse);
            }
            else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    private Weather getForcast(JSONObject jsonResponse) {
        final String description = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
        final double windSpeed = jsonResponse.getJSONObject("wind").getDouble("speed");
        final int humidity = jsonResponse.getJSONObject("main").getInt("humidity");
        final double temperature = jsonResponse.getJSONObject("main").getDouble("temp");
        return new Weather(description, temperature, humidity, windSpeed);
    }

}

