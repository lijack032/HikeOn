package use_case.locationSearch;

import java.util.List;

/**
 * An inputboundary interface for the location user case of HikeOnApp.
 */
public interface LocationInputBoundary {
    /**
     * Searches nearby hiking spots from the location correspond to the inputData.
     * @param inputData is the corresponding data structure to the location.
     * @return the data structure that corresponds to an array list of nearby hiking spots.
     */
    LocationOutputData searchHikingSpots(LocationInputData inputData);

    /**
     * Suggests possible location that the user is trying to type.
     * @param input is the current input in the search bar.
     * @return a list of possible completed location.
     */
    List<String> suggestLocations(String input);
}
