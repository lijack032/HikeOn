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
        assertEquals("Sunny", result.getCondition(), "Condition should match the mocked data.");
        assertEquals(25.0, result.getTemperature(), "Temperature should match the mocked data.");
        assertEquals(50.0, result.getHumidity(), "Humidity should match the mocked data.");
        assertEquals(10.0, result.getWindSpeed(), "Wind speed should match the mocked data.");
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

