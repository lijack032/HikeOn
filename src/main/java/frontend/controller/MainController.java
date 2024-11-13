package frontend.controller;

import frontend.view.MainFrame;
import frontend.view.panels.HomePanel;
import javax.swing.*;

public class MainController {
    private MainFrame mainFrame;
    private HomePanel homePanel;
    private WeatherController weatherController;
    private LocationController locationController;

    public MainController() {
        this.mainFrame = new MainFrame();
        this.homePanel = new HomePanel();
        this.weatherController = new WeatherController(homePanel);
        this.locationController = new LocationController(homePanel);

        setupListeners();
    }

    private void setupListeners() {
        mainFrame.getSearchButton().addActionListener(e -> locationController.handleSearch());
        mainFrame.getAIChatBotButton().addActionListener(e -> openChatBot());
    }

    public void start() {
        // Display the weather panel
        weatherController.displayWeather();
        SwingUtilities.invokeLater(() -> mainFrame.setVisible(true));
    }

    private void openChatBot() {
        JOptionPane.showMessageDialog(homePanel, "Opening chat bot...");
    }
}