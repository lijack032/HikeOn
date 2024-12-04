package entityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import entity.Weather;

public class WeatherTest {

    @Test
    public void testCurrentWeatherConstructor() {
        final Weather currentWeather = new Weather("clear sky", 25.0);
        
        assertNull(currentWeather.getTimestamp());
        assertEquals("clear sky", currentWeather.getCondition());
        assertEquals(25.0, currentWeather.getTemperature());
    }

    @Test
    public void testForecastWeatherConstructor() {
        final Weather forecastWeather = new Weather("2024-12-03 18:00:00", "rainy", 15.0);
        
        assertEquals("2024-12-03 18:00:00", forecastWeather.getTimestamp());
        assertEquals("rainy", forecastWeather.getCondition());
        assertEquals(15.0, forecastWeather.getTemperature());
    }

    @Test
    public void testSetters() {
        final Weather weather = new Weather("clear sky", 20.0);
        weather.setTimestamp("2024-12-04 10:00:00");
        weather.setCondition("cloudy");
        weather.setTemperature(18.5);

        assertEquals("2024-12-04 10:00:00", weather.getTimestamp());
        assertEquals("cloudy", weather.getCondition());
        assertEquals(18.5, weather.getTemperature());
    }
}
