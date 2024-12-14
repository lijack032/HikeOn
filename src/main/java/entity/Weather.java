package entity;

/**
 * Entity class representing weather data.
 */
public class Weather {
    private String timestamp;
    private String condition;
    private double temperature;

    /**
     * Constructor for current weather data.
     * 
     * @param condition the weather condition (e.g., "clear sky")
     * @param temperature the temperature in Celsius
     */
    public Weather(String condition, double temperature) {
        this.condition = condition;
        this.temperature = temperature;
    }
    
    /**
     * Constructor for forecast weather data.
     * 
     * @param timestamp the formatted timestamp (e.g., "2024-12-03 18:00:00")
     * @param condition the weather condition (e.g., "clear sky")
     * @param temperature the temperature in Celsius
     */
    public Weather(String timestamp, String condition, double temperature) {
        this.timestamp = timestamp;
        this.condition = condition;
        this.temperature = temperature;
    }

    // Getters
    public String getTimestamp() {
        return timestamp;
    }

    public String getCondition() {
        return condition;
    }

    public double getTemperature() {
        return temperature;
    }

    // Optional: Setters if needed
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
