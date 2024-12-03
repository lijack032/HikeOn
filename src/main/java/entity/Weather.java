package entity;

/**
 * Represents the different types of weather data.
 * 
 * @null This class does not support null values for its fields.
 */
public class Weather {
    private String condition;
    private double temperature;

    public Weather(String condition, double temperature) {
        this.condition = condition;
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public double getTemperature() {
        return temperature;
    }
    
}
