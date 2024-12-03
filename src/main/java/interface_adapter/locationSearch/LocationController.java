package interface_adapter.locationSearch;

import java.util.List;

import use_case.locationSearch.LocationInputBoundary;
import use_case.locationSearch.LocationInputData;

/**
 * The controller for the user case of location.
 */
public class LocationController {
    private final LocationInputBoundary interactor;

    public LocationController(LocationInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Searches nearby hiking spots from the location correspond to the inputData.
     * @param location is the input location.
     */
    public void searchHikingSpots(String location) {
        final LocationInputData inputData = new LocationInputData(location);
        interactor.searchHikingSpots(inputData);
    }

    /**
     * Suggests possible location that the user is trying to type.
     * @param input is the current input in the search bar.
     * @return a list of possible completed location.
     */
    public List<String> getSuggestions(String input) {
        return interactor.suggestLocations(input);
    }
}
