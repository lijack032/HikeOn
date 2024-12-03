package use_case.locationSearch;

/**
 * The data structure expected to be passed into the user case interactor for location-use cases.
 */
public class LocationInputData {
    private final String location;

    public LocationInputData(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
