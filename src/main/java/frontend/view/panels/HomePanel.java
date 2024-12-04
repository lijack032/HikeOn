//package frontend.view.panels;
//
//import java.awt.BorderLayout;
//import java.awt.event.ActionListener;
//import java.util.List;
//
//import javax.swing.JButton;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//
//import frontend.model.Location;
//import frontend.model.Weather;
//
///**
// * HomePanel class represents the main panel of the application.
// * It contains weather, location, and chatbot panels, as well as a search bar.
// * @null
// */
//public class HomePanel extends JPanel {
//    private WeatherPanel weatherPanel;
//    private LocationPanel locationPanel;
//    private ChatbotPanel chatbotPanel;
//    private JTextField searchField;
//    private JButton searchButton;
//
//    public HomePanel() {
//        setLayout(new BorderLayout());
//
//        // Initialize panels
//        weatherPanel = new WeatherPanel();
//        locationPanel = new LocationPanel();
//        chatbotPanel = new ChatbotPanel();
//
//        // Search bar setup
//        final JPanel searchPanel = new JPanel(new BorderLayout());
//        searchField = new JTextField("Enter activity (e.g., hiking, picnic)");
//        searchButton = new JButton("Search");
//
//        searchPanel.add(searchField, BorderLayout.CENTER);
//        searchPanel.add(searchButton, BorderLayout.EAST);
//
//        // Layout panels
//
//        // Weather info at the top
//        add(weatherPanel, BorderLayout.NORTH);
//        // Search bar in the center
//        add(searchPanel, BorderLayout.CENTER);
//        // Location results below
//        add(locationPanel, BorderLayout.SOUTH);
//        // Chatbot on the right
//        add(chatbotPanel, BorderLayout.EAST);
//    }
//
//    public String getSearchQuery() {
//        return searchField.getText();
//    }
//
//    /**
//     * Sets the action listener for the search button.
//     * @param listener the ActionListener to be set
//     */
//    public void setSearchButtonListener(ActionListener listener) {
//        searchButton.addActionListener(listener);
//    }
//
//    /**
//     * Updates the weather panel with the given weather information.
//     * @param weather the Weather object containing weather information
//     */
//    public void updateWeatherPanel(Weather weather) {
//        weatherPanel.updateWeather(weather);
//    }
//
//    /**
//     * Displays the location results in the location panel.
//     * @param locations the list of Location objects to be displayed
//     */
//    public void displayLocationResults(List<Location> locations) {
//        locationPanel.displayLocationResults(locations);
//    }
//
//    public ChatbotPanel getChatbotPanel() {
//        return chatbotPanel;
//    }
//}
