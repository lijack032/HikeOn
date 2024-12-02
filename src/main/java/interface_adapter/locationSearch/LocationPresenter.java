package interface_adapter.locationSearch;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import entity.HikingSpot;
import use_case.locationSearch.LocationOutputBoundary;
import use_case.locationSearch.LocationOutputData;

/**
 * Presenter for displaying hiking spot results in a new frame.
 * Results are ranked by weighted ratings and displayed three per row.
 */
public class LocationPresenter implements LocationOutputBoundary {

    @Override
    public void presentLocationResults(LocationOutputData outputData) {
        final List<HikingSpot> hikingSpots = outputData.getHikingSpots();

        if (hikingSpots.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hiking spots found.",
                    "No Results", JOptionPane.INFORMATION_MESSAGE);

        }
        else {
            // Sort hiking spots by weighted rating (rating * user ratings total)
            hikingSpots.sort(this::sortWeightedRating);

            // Create results frame
            final JFrame resultsFrame = new JFrame("Nearby Hiking Spots for " + outputData.getInputname());
            resultsFrame.setSize(1000, 600);
            resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            final JPanel resultPanel = new JPanel(new GridLayout(0, 3, 10, 10));
            resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Populate the result panel with hiking spots
            for (int i = hikingSpots.size() - 1; i >= 0; i--) {
                resultPanel.add(createSpotPanel(hikingSpots.get(i)));
            }

            final JScrollPane scrollPane = new JScrollPane(resultPanel);
            resultsFrame.add(scrollPane);
            resultsFrame.setVisible(true);
        }
    }

    /**
     * Creates a JPanel representing a single hiking spot.
     *
     * @param spot The HikingSpot to display.
     * @return A JPanel containing the hiking spot's details.
     */
    private JPanel createSpotPanel(HikingSpot spot) {
        final JPanel spotPanel = new JPanel();
        spotPanel.setLayout(new GridLayout(4, 1));
        spotPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        spotPanel.setBackground(Color.WHITE);

        // Name Label (Bold, Centered)
        final JLabel nameLabel = new JLabel("<html><b>" + spot.getName() + "</b></html>", SwingConstants.CENTER);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Rating Label
        final String ratingText;
        if (spot.getRating() != null) {
            ratingText = "Rating: " + spot.getRating();
        }
        else {
            ratingText = "Rating: No rating";
        }
        final JLabel ratingLabel = new JLabel(ratingText, SwingConstants.CENTER);
        ratingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Total Ratings Label
        final String totalRatingsText;
        if (spot.getUserRatingsTotal() != null) {
            totalRatingsText = "Total Ratings: " + spot.getUserRatingsTotal();
        }
        else {
            totalRatingsText = "Total Ratings: No ratings";
        }
        final JLabel totalRatingsLabel = new JLabel(totalRatingsText, SwingConstants.CENTER);
        totalRatingsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // View on Map Button
        final JButton mapButton = new JButton("View on Map");
        mapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGoogleMaps(spot);
            }
        });

        // Add components to the panel
        spotPanel.add(nameLabel);
        spotPanel.add(ratingLabel);
        spotPanel.add(totalRatingsLabel);
        spotPanel.add(mapButton);

        return spotPanel;
    }

    /**
     * Opens Google Maps for the given hiking spot.
     *
     * @param spot The HikingSpot to view on Google Maps.
     */
    private void openGoogleMaps(HikingSpot spot) {
        try {
            final String encodedName = URLEncoder.encode(spot.getName(), StandardCharsets.UTF_8);
            final String mapUrl = String.format("https://www.google.com/maps/search/%s/@%s,%s,12z/data=!3m1"
                            + "!4b1?entry=ttu&g_ep=EgoyMDI0MTEyNC4xIKXMDSoASAFQAw%%3D%%3D",
                    encodedName, spot.getLatitude(), spot.getLongitude());
            Desktop.getDesktop().browse(new java.net.URI(mapUrl));
        }
        catch (URISyntaxException | IOException exception) {
            JOptionPane.showMessageDialog(null, "Unable to open map.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Calculates the weighted rating of a hiking spot.
     *
     * @param spot The HikingSpot to calculate for.
     * @return The weighted rating (rating * user ratings total).
     */
    private double getWeightedRating(HikingSpot spot) {
        double value = 0;
        if (spot.getRating() != null && spot.getUserRatingsTotal() != null) {
            value = spot.getRating() * spot.getUserRatingsTotal();
        }
        return value;
    }

    private int sortWeightedRating(HikingSpot spotone, HikingSpot spottwo) {
        return Double.compare(getWeightedRating(spotone), getWeightedRating(spottwo));
    }
}
