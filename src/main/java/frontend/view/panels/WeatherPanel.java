package frontend.view.panels;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import frontend.model.Weather;

/**
 * WeatherPanel displays weather information.
 * 
 * @null This class does not accept null values.
 */
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

//    /**
//     * Updates the weather information displayed on the panel.
//     *
//     * @param weather the Weather object containing updated weather information
//     */
//    public void updateWeather(Weather weather) {
//        conditionLabel.setText("Condition: " + weather.getCondition());
//        temperatureLabel.setText("Temperature: " + weather.getTemperature() + " degrees C");
//        humidityLabel.setText("Humidity: " + weather.getHumidity() + "%");
//        windSpeedLabel.setText("Wind Speed: " + weather.getWindSpeed() + " km/h");
//    }
}
