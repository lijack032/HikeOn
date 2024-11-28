package api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import frontend.model.Weather;
import frontend.utils.ApiUtils;

public class WeatherApiTest {

    @Test
    void testFetchCurrentWeatherRealApi() {
        final Weather weather = ApiUtils.fetchCurrentWeather("yourArgumentHere");

        // Ensure the response is not null
        assertNotNull(weather);

        // Ensure the values are within a valid range
        assertTrue(weather.getTemperature() >= -50 && weather.getTemperature() <= 50);
        assertTrue(weather.getHumidity() >= 0 && weather.getHumidity() <= 100);
    }

}
