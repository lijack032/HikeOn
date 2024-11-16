package frontend.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import frontend.model.Location;
import frontend.model.Weather;

public class HomePanel extends JPanel {
    private WeatherPanel weatherPanel;
    private LocationPanel locationPanel;
    private ChatbotPanel chatbotPanel;
    private JTextField searchField;
    private JButton searchButton;

    public HomePanel() {
        setLayout(new BorderLayout());

        // Initialize panels
        weatherPanel = new WeatherPanel();
        locationPanel = new LocationPanel();
        chatbotPanel = new ChatbotPanel();

        // Search bar setup
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField("Enter activity (e.g., hiking, picnic)");
        searchButton = new JButton("Search");

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Layout panels
        add(weatherPanel, BorderLayout.NORTH);       // Weather info at the top
        add(searchPanel, BorderLayout.CENTER);       // Search bar in the center
        add(locationPanel, BorderLayout.SOUTH);      // Location results below
        add(chatbotPanel, BorderLayout.EAST);        // Chatbot on the right
    }

    public String getSearchQuery() {
        return searchField.getText();
    }

    public void setSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void updateWeatherPanel(Weather weather) {
        weatherPanel.updateWeather(weather);
    }

    public void displayLocationResults(List<Location> locations) {
        locationPanel.displayLocationResults(locations);
    }

    public ChatbotPanel getChatbotPanel() {
        return chatbotPanel;
    }
}