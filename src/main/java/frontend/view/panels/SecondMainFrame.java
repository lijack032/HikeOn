package frontend.view.panels;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import backend.service.WeatherService;

public class SecondMainFrame {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("HikeOn - Outdoor Event Planner");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        final JPanel titlePanel = new JPanel();
        final JLabel titleLabel = new JLabel("HikeOn");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(34, 139, 34));
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);

        final JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        final JLabel eventLabel = new JLabel("Event Type:");
        eventLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        final String[] events = {"Hiking", "Picnic"};
        final JComboBox<String> eventComboBox = new JComboBox<>(events);

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(eventLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(eventComboBox, gbc);

        final JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        final JTextField locationField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(locationLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(locationField, gbc);

        final JLabel weatherLabel = new JLabel("Weather: ");
        weatherLabel.setFont(new Font("Arial", Font.BOLD, 16));
        weatherLabel.setForeground(Color.BLUE);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(weatherLabel, gbc);

        final JButton fetchWeatherButton = createStyledButton("Get Weather", new Color(70, 130, 180), new Color(100, 149, 237));
        fetchWeatherButton.addActionListener(e -> {
            String location = locationField.getText();
            try {
                String weatherInfo = (new WeatherService()).getWeather(location);
                weatherLabel.setText("<html>" + weatherInfo.replace("\n", "<br>") + "</html>");
            } catch (IOException ex) {
                weatherLabel.setText("Error fetching weather data.");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(fetchWeatherButton, gbc);

        final JButton googleMapsButton = createStyledButton("Find Nearby Hiking Trails", new Color(34, 139, 34), new Color(60, 179, 113));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        centerPanel.add(googleMapsButton, gbc);

        frame.add(centerPanel, BorderLayout.CENTER);

        final JPanel footerPanel = new JPanel();
        final JLabel footerLabel = new JLabel("Stay safe and enjoy the outdoors!");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerPanel.add(footerLabel);
        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text, Color defaultColor, Color hoverColor) {
        final JButton button = new JButton(text);
        return button;
    }
}


