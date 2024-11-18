package frontend.controller;

import java.util.List;

import backend.service.LocationService;
import frontend.model.Location;
import frontend.view.panels.LocationPanel;

/**
 * Controller for handling location-related actions.
 * This class is responsible for handling search requests and updating the location panel with the results.
 */
public class LocationController {
    private final LocationPanel locationPanel;
    private final LocationService locationService;
    
    public LocationController(LocationPanel locationPanel, LocationService locationService) {
        this.locationPanel = locationPanel;
        this.locationService = locationService;
    }

    /**
     * Searches for locations based on the provided query and updates the location panel with the results.
     *
     * @param query the search query, must not be null or empty
     */
    public void searchLocations(String query) {
        if (query == null || query.trim().isEmpty()) {
            // Show no results if the query is empty
            locationPanel.displayLocationResults(List.of());
        } 
        else {
            // Fetch the locations based on the user's query (e.g., "hiking", "picnic")
            final List<Location> locations = locationService.searchLocations(query);
            // Update the LocationPanel with the fetched locations
            locationPanel.displayLocationResults(locations);
        }
    }
}
