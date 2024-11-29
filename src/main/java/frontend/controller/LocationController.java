package frontend.controller;

import backend.service.LocationService;
import backend.service.LocationService.GeoLocation;
import backend.service.LocationService.HikingSpot;
import frontend.utils.LocationNameConverter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LocationController {
    private LocationService locationService;

    public LocationController() {
        locationService = new LocationService();
    }

    public void searchHikingSpots(String location) {

        try {
            GeoLocation geoLocation = locationService.getCoordinates(location);
            List<HikingSpot> spots = locationService.findNearbyHikingSpots(geoLocation);

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
            Double weightedRating1 = getWeightedRating(spot1);
            Double weightedRating2 = getWeightedRating(spot2);
            return weightedRating2.compareTo(weightedRating1); // Sort in descending order
        });

        JFrame resultsFrame = new JFrame("Hiking Spot for " + LocationNameConverter
                .capitalizeLocationName(location));
        resultsFrame.setSize(1000, 600);
        resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(0, 3, 10, 5));

        JScrollPane scrollPane = new JScrollPane(resultPanel);
        resultsFrame.add(scrollPane);

        if (spots.isEmpty()) {
            resultPanel.add(new JLabel("No hiking spots found for location: " + LocationNameConverter
                    .capitalizeLocationName(location)));
        }
        else {
            // Display sorted hiking spots
            for (HikingSpot spot : spots) {
                JPanel spotPanel = new JPanel();
                spotPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

                JButton spotButton = new JButton(spot.getName());
                spotButton.addActionListener(e -> openMapInBrowser(spot));

                spotButton.setPreferredSize(new Dimension(150, 60));
                spotButton.setMaximumSize(new Dimension(150, 60));
                spotPanel.add(spotButton);

                String ratingText = (spot.getRating() != null ? spot.getRating().toString() : "No rating available");
                String userRatingsText = (spot.getUserRatingsTotal() != null ? spot.getUserRatingsTotal().toString() : "No ratings available");

                JLabel spotLabel = new JLabel("<html>Rating: " + ratingText + "<br>User Ratings: " + userRatingsText + "</html>");
                spotPanel.add(spotLabel);

                resultPanel.add(spotPanel);
            }
        }

        resultsFrame.setVisible(true);
    }

    // Helper method to calculate the weighted rating of a hiking spot
    private double getWeightedRating(HikingSpot spot) {
        Double rating = spot.getRating();
        Integer userRatingsTotal = spot.getUserRatingsTotal();
        if (rating != null && userRatingsTotal != null) {
            return rating * userRatingsTotal; // Rating * Number of User Ratings
        }
        return 0; // Return 0 if either rating or user ratings total is null
    }


    private void openMapInBrowser(HikingSpot spot) {
        String mapUrl = String.format("https://www.google.com/maps/search/%s/@%s,%s,12z/data=!3m1!4b1?entry=ttu&g_ep=EgoyMDI0MTEyNC4xIKXMDSoASAFQAw%%3D%%3D",
                spot.getName().replace(" ", "+"), spot.getLatitude(), spot.getLongitude());
        try {
            Desktop.getDesktop().browse(new java.net.URI(mapUrl));
        } catch (Exception e) {
            System.out.println("Error opening map in browser: " + e.getMessage());
        }
    }
}
