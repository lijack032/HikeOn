package frontend.model;

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
