package frontend.view.panels;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import frontend.model.Location;

public class LocationPanel extends JPanel {
    private JTextArea locationResultsArea;

    public LocationPanel() {
        setLayout(new BorderLayout());

        locationResultsArea = new JTextArea();
        locationResultsArea.setEditable(false);
        locationResultsArea.setLineWrap(true);
        locationResultsArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(locationResultsArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayLocationResults(List<Location> locations) {
        locationResultsArea.setText("");  // Clear previous results
        if (locations.isEmpty()) {
            locationResultsArea.append("No locations found.");
        } else {
            for (Location location : locations) {
                locationResultsArea.append(location.getName() + "\n");
                locationResultsArea.append("Address: " + location.getName() + "\n\n");
            }
        }
    }
}
