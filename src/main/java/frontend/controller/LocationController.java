package frontend.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import backend.service.LocationService;
import backend.service.LocationService.GeoLocation;
import frontend.model.HikingSpot;
import frontend.utils.LocationNameConverter;

/**
 * Controller for handling location interaction.
 * Manages user inputs and google place search output as additional JFrame.
 */
public class LocationController {
    private static final int WIDTH_FOR_RESULT_FRAME = 1000;
    private static final int HEIGHT_FOR_RESULT_FRAME = 600;
    private static final int COLUMNS_IN_RESULT_FRAME = 3;
    private static final int HORIZONTAL_GAP_IN_RESULT_FRAME = 10;
    private static final int VERTICAL_GAP_IN_RESULT_FRAME = 5;
    private static final int WIDTH_FOR_SPOT_BUTTON = 150;
    private static final int HEIGHT_FOR_SPOT_BUTTON = 60;

    private final LocationService locationService;

    public LocationController() {
        locationService = new LocationService();
    }

    /**
     * Method responsible for passing the location the user entered to the use case.
     * @param location is the user's input.
     * @throws IllegalArgumentException if the input is invalid.
     */
    public void searchHikingSpots(String location) {
        try {
            final GeoLocation geoLocation = locationService.getCoordinates(location);
            final List<HikingSpot> spots = locationService.findNearbyHikingSpots(geoLocation, location);

            // Show results in a new frame
            showHikingSpotResults(spots, location);
        }
        catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException();
        }
    }

    private void showHikingSpotResults(List<HikingSpot> spots, String location) {
        // Sort spots by weighted rating (Rating * Number of User Ratings)
        spots.sort((spot1, spot2) -> {
            final Double weightedRating1 = getWeightedRating(spot1);
            final Double weightedRating2 = getWeightedRating(spot2);
            return weightedRating2.compareTo(weightedRating1);
        });

        final JFrame resultsFrame = new JFrame("Hiking Spot for " + LocationNameConverter
                .capitalizeLocationName(location));
        resultsFrame.setSize(WIDTH_FOR_RESULT_FRAME, HEIGHT_FOR_RESULT_FRAME);
        resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(0, COLUMNS_IN_RESULT_FRAME, HORIZONTAL_GAP_IN_RESULT_FRAME,
                VERTICAL_GAP_IN_RESULT_FRAME));

        final JScrollPane scrollPane = new JScrollPane(resultPanel);
        resultsFrame.add(scrollPane);

        if (spots.isEmpty()) {
            resultPanel.add(new JLabel("No hiking spots found for location: " + LocationNameConverter
                    .capitalizeLocationName(location)));
        }
        else {
            // Display sorted hiking spots
            for (HikingSpot spot : spots) {
                final JPanel spotPanel = new JPanel();
                spotPanel.setLayout(new FlowLayout(FlowLayout.LEFT, HORIZONTAL_GAP_IN_RESULT_FRAME,
                        VERTICAL_GAP_IN_RESULT_FRAME));

                final JButton spotButton = getSpotButton(spot);
                spotPanel.add(spotButton);

                final JLabel spotLabel = getSpotLabel(spot);
                spotPanel.add(spotLabel);

                resultPanel.add(spotPanel);
            }
        }

        resultsFrame.setVisible(true);
    }

    @NotNull
    private JButton getSpotButton(HikingSpot spot) {
        final JButton spotButton = new JButton(spot.getName());
        spotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openMapInBrowser(spot);
                }
                catch (URISyntaxException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        spotButton.setPreferredSize(new Dimension(WIDTH_FOR_SPOT_BUTTON, HEIGHT_FOR_SPOT_BUTTON));
        spotButton.setMaximumSize(new Dimension(WIDTH_FOR_SPOT_BUTTON, HEIGHT_FOR_SPOT_BUTTON));
        return spotButton;
    }

    @NotNull
    private static JLabel getSpotLabel(HikingSpot spot) {
        final String ratingText;
        if (spot.getRating() != null) {
            ratingText = spot.getRating().toString();
        }
        else {
            ratingText = "No rating available";
        }

        final String userRatingsText;
        if (spot.getUserRatingsTotal() != null) {
            userRatingsText = spot.getUserRatingsTotal().toString();
        }
        else {
            userRatingsText = "No ratings available";
        }

        return new JLabel("<html>Rating: " + ratingText + "<br>Total Ratings: " + userRatingsText + "</html>");

    }

    /**
     * Handle user input with suggestions and allow confirmation.
     *
     * @param input the user's location input
     */
    public void handleLocationWithSuggestions(String input) {
        // Fetch location suggestions from the service
        final List<String> suggestions = locationService.suggestLocations(input);

        if (!suggestions.isEmpty()) {
            // Normalize user input for case-insensitive comparison
            final String normalizedInput = input.trim().toLowerCase();

            // Check if the input matches any suggestion
            final boolean isExactMatch = suggestions.stream()
                    .map(String::toLowerCase)
                    .anyMatch(suggestion -> suggestion.equals(normalizedInput));

            if (isExactMatch) {
                // Input matches a valid city name; proceed without prompting
                searchHikingSpots(input);
            }
            else {
                // Input does not match; show "Did you mean..." prompt
                final String topSuggestion = suggestions.get(0);
                final int response = JOptionPane.showConfirmDialog(null,
                        "Do you mean: " + topSuggestion + "?", "Location Suggestion",
                        JOptionPane.YES_NO_OPTION);

                if (response == JOptionPane.YES_OPTION) {
                    // User accepts the suggestion
                    searchHikingSpots(topSuggestion);
                }
                else {
                    // User declines the suggestion
                    JOptionPane.showMessageDialog(null, "Please refine your input and try again.");
                }
            }
        }
        else {
            // No suggestions found for the input
            JOptionPane.showMessageDialog(null, "No suggestions found for: " + input);
        }
    }

    // Helper method to calculate the weighted rating of a hiking spot
    private double getWeightedRating(HikingSpot spot) {
        final Double rating = spot.getRating();
        final Integer userRatingsTotal = spot.getUserRatingsTotal();
        double value = 0;
        if (rating != null && userRatingsTotal != null) {
            value = rating * userRatingsTotal;
        }
        return value;
    }

    private void openMapInBrowser(HikingSpot spot) throws URISyntaxException, IOException {
        final String encodedName = URLEncoder.encode(spot.getName(), StandardCharsets.UTF_8);
        final String mapUrl = String.format("https://www.google.com/maps/search/%s/@%s,%s,12z/data=!3m1"
                        + "!4b1?entry=ttu&g_ep=EgoyMDI0MTEyNC4xIKXMDSoASAFQAw%%3D%%3D",
                encodedName, spot.getLatitude(), spot.getLongitude());
        Desktop.getDesktop().browse(new java.net.URI(mapUrl));
    }
}
