package frontend.model;

/**
 * Represents the weather conditions.
 * 
 * @null
 */
public class Weather {
    private String condition;
    private double temperature;
    private double humidity;
    private double windSpeed;

    public Weather(String condition, double temperature, double humidity, double windSpeed) {
        this.condition = condition;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public Weather(String weatherData) {
        // Initialize the Weather object using the weatherData string
        
        // Example: parse the weatherData string and set the fields accordingly

    }

    public String getCondition() {
        return condition;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
    
}
