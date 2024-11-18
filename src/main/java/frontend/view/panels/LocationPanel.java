package frontend.view.panels;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import frontend.model.Location;

/**
 * A panel to display location results.
 * 
 * @null This class does not accept null values for its fields.
 */
public class LocationPanel extends JPanel {
    private JTextArea locationResultsArea;

    public LocationPanel() {
        setLayout(new BorderLayout());

        locationResultsArea = new JTextArea();
        locationResultsArea.setEditable(false);
        locationResultsArea.setLineWrap(true);
        locationResultsArea.setWrapStyleWord(true);

        final JScrollPane scrollPane = new JScrollPane(locationResultsArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Displays the location results in the text area.
     * 
     * @param locations the list of locations to display
     */
    public void displayLocationResults(List<Location> locations) {
        // Clear the text area
        locationResultsArea.setText(""); 
        if (locations.isEmpty()) {
            locationResultsArea.append("No locations found.");
        } 
        else {
            for (Location location : locations) {
                locationResultsArea.append(location.getName() + "\n");
                locationResultsArea.append("Address: " + location.getName() + "\n\n");
            }
        }
    }
}
