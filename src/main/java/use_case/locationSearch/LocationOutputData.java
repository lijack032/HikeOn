package use_case.locationSearch;

import java.util.List;

import entity.HikingSpot;

/**
 * The data structure to be passed into the presenter from the user case interactor of location.
 */
public class LocationOutputData {
    private final List<HikingSpot> hikingSpots;
    private final String inputname;

    public LocationOutputData(List<HikingSpot> hikingSpots, String inputname) {
        this.hikingSpots = hikingSpots;
        this.inputname = inputname;
    }

    public List<HikingSpot> getHikingSpots() {
        return hikingSpots;
    }

    public String getInputname() {
        return inputname;
    }
}

