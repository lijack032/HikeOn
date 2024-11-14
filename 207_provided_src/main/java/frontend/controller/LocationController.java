package frontend.controller;

import frontend.view.panels.HomePanel;
import frontend.model.Location;
import frontend.utils.APIUtils;

public class LocationController {
    private HomePanel homePanel;

    public LocationController(HomePanel homePanel) {
        this.homePanel = homePanel;
    }

    public void handleSearch() {
        String location = homePanel.getLocation();
        if (!location.isEmpty()) {
            String[] locationData = location.split(",");
            homePanel.displaySearchResults(locationData);
        }
        else {
            homePanel.displaySearchResults("Please enter a location type.");
        }
    }
}
