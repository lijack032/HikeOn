package BackendTest;

import frontend.model.Weather;
import backend.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        weatherService = new WeatherService();
    }

    @Test
    void testGetCurrentWeather_ValidCity() throws Exception {
        injectMockWeather(new Weather("Sunny", 25.0, 50.0, 10.0));

        Weather result = weatherService.getCurrentWeather("New York");

        assertNotNull(result, "Weather should not be null for a valid city.");
        assertNotNull(result.getCondition(), "Condition should not be null for a valid city.");
        assertNotNull(result.getTemperature(), "Temperature should not be null for a valid city.");
        assertNotNull(result.getHumidity(), "Humidity should not be null for a valid city.");
        assertNotNull(result.getWindSpeed(), "Wind speed should not be null for a valid city.");
    }

    @Test
    void testGetCurrentWeather_InvalidCity() throws Exception {
        injectMockWeather(null);

        Weather result = weatherService.getCurrentWeather("InvalidCity");

        assertNull(result, "Weather should be null for an invalid city.");
    }

    @Test
    void testGetCurrentWeather_EmptyCity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            weatherService.getCurrentWeather("");
        });
        assertEquals("City name cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testGetCurrentWeather_NullCity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            weatherService.getCurrentWeather(null);
        });
        assertEquals("City name cannot be null or empty.", exception.getMessage());
    }

    private void injectMockWeather(Weather mockWeather) throws Exception {
        Field currentWeatherField = WeatherService.class.getDeclaredField("currentWeather");
        currentWeatherField.setAccessible(true);
        currentWeatherField.set(weatherService, mockWeather);
    }
}

