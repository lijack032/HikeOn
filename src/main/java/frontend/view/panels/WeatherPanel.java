package frontend.view.panels;

import javax.swing.*;
import frontend.model.Weather;

public class WeatherPanel extends JPanel {
    private JLabel conditionLabel;
    private JLabel temperatureLabel;
    private JLabel humidityLabel;
    private JLabel windSpeedLabel;

    public WeatherPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        conditionLabel = new JLabel("Condition: ");
        temperatureLabel = new JLabel("Temperature: ");
        humidityLabel = new JLabel("Humidity: ");
        windSpeedLabel = new JLabel("Wind Speed: ");

        add(conditionLabel);
        add(temperatureLabel);
        add(humidityLabel);
        add(windSpeedLabel);
    }

    public void updateWeather(Weather weather) {
        conditionLabel.setText("Condition: " + weather.getCondition());
        temperatureLabel.setText("Temperature: " + weather.getTemperature() + "°C");
        humidityLabel.setText("Humidity: " + weather.getHumidity() + "%");
        windSpeedLabel.setText("Wind Speed: " + weather.getWindSpeed() + " km/h");
    }
}
