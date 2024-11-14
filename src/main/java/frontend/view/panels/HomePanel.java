package com.hikeon.frontend.view.panels;

import javax.swing.*;
import com.hikeon.frontend.controller.WeatherController;
import com.hikeon.frontend.controller.LocationController;
import java.awt.*;
import java.awt.event.ActionListener;
import com.hikeon.frontend.model.Location;
import java.util.List;

public class HomePanel extends JPanel {

    private JLabel weatherLabel;
    private JTextField searchBar;
    private JButton searchButton;
    private JButton aiChatbotButton;
    private JTextArea suggestionsArea;
    private WeatherController weatherController;
    private LocationController locationController;

    public HomePanel() {
        this.weatherController = new WeatherController(this);
        this.locationController = new LocationController(this);

        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Top Panel for AI Chatbot Button
        JPanel topPanel = new JPanel(new BorderLayout());
        aiChatbotButton = new JButton("AI Chatbot");
        aiChatbotButton.addActionListener(e -> openChatbot());
        topPanel.add(aiChatbotButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel for Weather and Search
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Weather Display
        weatherLabel = new JLabel("Loading weather...");
        weatherLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(weatherLabel);

        // Search Bar
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchBar = new JTextField(20);
        searchButton = new JButton("Search Trails/Parks");
        searchPanel.add(searchBar);
        searchPanel.add(searchButton);

        centerPanel.add(searchPanel);

        // Suggestions Area
        suggestionsArea = new JTextArea(10, 30);
        suggestionsArea.setEditable(false);
        centerPanel.add(new JScrollPane(suggestionsArea));

        add(centerPanel, BorderLayout.CENTER);

        // Search Button Action
        searchButton.addActionListener(e -> locationController.handleSearch());
    }

    // Methods to update UI from controllers
    public void updateWeatherLabel(String condition, int temperature) {
        weatherLabel.setText("Current Weather: " + condition + ", " + temperature + "°C");
    }

    public String getSearchQuery() {
        return searchBar.getText();
    }

    public void displaySearchResults(String message) {
        suggestionsArea.setText(message);
    }

    public void displaySearchResults(List<Location> locations) {
        suggestionsArea.setText(""); // Clear previous results
        for (Location location : locations) {
            suggestionsArea.append(location.getName() + "\n");
        }
    }

    private void openChatbot() {
        JOptionPane.showMessageDialog(this, "Opening Chatbot...");
        // Chatbot logic to be added here
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getAiChatbotButton() {
        return aiChatbotButton;
    }
}