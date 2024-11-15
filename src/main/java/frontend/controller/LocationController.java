package frontend.controller;

import frontend.view.panels.LocationPanel;
import backend.service.LocationService;
import frontend.model.Location;

import java.util.List;

public class LocationController {
    private final LocationPanel locationPanel;
    private final LocationService locationService;

    public LocationController(LocationPanel locationPanel, LocationService locationService) {
        this.locationPanel = locationPanel;
        this.locationService = locationService;
    }

    // Method to handle search request and display results
    public void searchLocations(String query) {
        if (query == null || query.trim().isEmpty()) {
            locationPanel.displayLocationResults(List.of());  // Show no results if the query is empty
            return;
        }

        // Fetch the locations based on the user's query (e.g., "hiking", "picnic")
        List<Location> locations = locationService.searchLocations(query);
        locationPanel.displayLocationResults(locations);  // Update the LocationPanel with the fetched locations
    }
}
