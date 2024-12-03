package use_case_test;

import entity.GeoLocation;
import entity.HikingSpot;
import frontend.utils.HttpClientStub;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import use_case.locationSearch.LocationInputData;
import use_case.locationSearch.LocationInteractor;
import use_case.locationSearch.LocationOutputBoundary;
import use_case.locationSearch.LocationOutputData;

import java.util.List;

import static org.junit.Assert.*;

public class LocationInteractorTest {

    private LocationInteractor interactor;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        interactor = new LocationInteractor(new LocationOutputBoundary() {
            @Override
            public void presentLocationResults(LocationOutputData outputData) {
                // No-op for testing
            }
        });
    }

    @Test
    public void testSuggestLocations_Success() throws Exception {
        // Perform the test
        List<String> suggestions = interactor.suggestLocations("New");

        // Assert results
        assertNotNull(suggestions);
        assertFalse(suggestions.isEmpty());
        for (String suggestion : suggestions) {
            assertNotNull(suggestion);
        }
    }

    @Test
    public void testGetCoordinates_Success() throws Exception {
        // Directly call the package-private method
        GeoLocation geoLocation = interactor.getCoordinates("New York City");

        // Assert results
        assertNotNull(geoLocation);
        assertTrue(geoLocation.getLatitude() >= -90 && geoLocation.getLatitude() <= 90);
        assertTrue(geoLocation.getLongitude() >= -180 && geoLocation.getLongitude() <= 180);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCoordinates_InvalidLocation() throws Exception {
        // Stub HttpClient static methods
        HttpClientStub.stubSendGetRequest("geocode", """
                {
                  "results": []
                }
                """);

        // Directly call the package-private method
        interactor.getCoordinates("Invalid Location");
    }

    @Test
    public void testFindNearbyHikingSpots_Success() throws Exception {
        // Perform the test
        GeoLocation geoLocation = new GeoLocation(40.7128, -74.0060);
        List<HikingSpot> spots = interactor.findNearbyHikingSpots(geoLocation, "New York City");

        // Assert results
        assertNotNull(spots);
        assertFalse(spots.isEmpty());
        for (HikingSpot spot : spots) {
            assertNotNull(spot);
            assertNotNull(spot.getName());
            assertTrue(spot.getLatitude() >= -90 && spot.getLatitude() <= 90);
            assertTrue(spot.getLongitude() >= -180 && spot.getLongitude() <= 180);
        }
    }

    @Test
    public void testSearchHikingSpots_Success() throws Exception {
        // Perform the test
        LocationInputData inputData = new LocationInputData("New York City");
        LocationOutputData outputData = interactor.searchHikingSpots(inputData);

        // Assert results
        assertNotNull(outputData);
        List<HikingSpot> spots = outputData.getHikingSpots();
        assertNotNull(spots);
        assertFalse(spots.isEmpty());
        for (HikingSpot spot : spots) {
            assertNotNull(spot);
            assertNotNull(spot.getName());
            assertTrue(spot.getLatitude() >= -90 && spot.getLatitude() <= 90);
            assertTrue(spot.getLongitude() >= -180 && spot.getLongitude() <= 180);
        }
    }

    @Test
    public void testSearchHikingSpots_NoResults() throws IllegalStateException {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    LocationInputData inputData = new LocationInputData("book");
                    LocationOutputData outputData = interactor.searchHikingSpots(inputData);
                });

        // Assert results
        assertEquals("Invalid location!", exception.getMessage());
    }

    @Test
    public void testSuggestLocations_NoResults() throws Exception {

        // Perform the test
        List<String> suggestions = interactor.suggestLocations("");

        // Assert results
        assertNotNull(suggestions);
        assertTrue(suggestions.isEmpty());
    }

    @Test
    public void testFindNearbyHikingSpots_EmptyLocation() throws Exception {

        // Perform the test
        GeoLocation geoLocation = new GeoLocation(40.7128, -74.0060);
        List<HikingSpot> spots = interactor.findNearbyHikingSpots(geoLocation, "");

        // Assert results
        assertNotNull(spots);
        assertTrue(!spots.isEmpty());
    }
}