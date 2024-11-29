package backend.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import frontend.model.HikingSpot;
import frontend.utils.HttpClient;

/**
 * Service for handling giving hiking location recommendation using Google Place Search.
 */
public class LocationService {
    private static final String GOOGLE_API_KEY = "";
    private static final String GEOMETRY = "geometry";
    private static final String LOCATION = "location";

    /**
     * Get the geographical coordinates (latitude and longitude) for a given location.
     *
     * @param location the location to get coordinates for
     * @return a GeoLocation object containing latitude and longitude
     * @throws IllegalArgumentException if the location is not valid
     */
    public GeoLocation getCoordinates(String location) {
        final String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="
                + location.replace(" ", "+") + "&key=" + GOOGLE_API_KEY;

        // Use the HttpClient utility to send a GET request
        final String response = HttpClient.sendGetRequest(apiUrl);

        // Parse the response JSON
        final JSONObject responseJson = new JSONObject(response);
        final JSONArray results = responseJson.getJSONArray("results");

        if (!results.isEmpty()) {
            final JSONObject locationJson = results.getJSONObject(0)
                    .getJSONObject(GEOMETRY).getJSONObject(LOCATION);
            final double latitude = locationJson.getDouble("lat");
            final double longitude = locationJson.getDouble("lng");
            return new GeoLocation(latitude, longitude);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Find nearby hiking spots for a given geo-location.
     *
     * @param geoLocation the geographical location (latitude, longitude) to search nearby hiking spots
     * @return a list of HikingSpot objects representing the nearby hiking trails
     */
    public List<HikingSpot> findNearbyHikingSpots(GeoLocation geoLocation) {
        final String apiUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?"
                + "query=hiking+trail+" + "&location=" + geoLocation.getLatitude() + "," + geoLocation.getLongitude()
                + "&radius=500000" + "&rankby=prominence" + "&key=" + GOOGLE_API_KEY;

        // Use the HttpClient utility to send a GET request
        final String response = HttpClient.sendGetRequest(apiUrl);

        // Parse the response and convert to HikingSpot objects
        return parseHikingSpots(response);
    }

    /**
     * Parse the response from the Google Places API and convert it into a list of HikingSpot objects.
     *
     * @param response the JSON response string from the Google Places API
     * @return a list of HikingSpot objects
     */
    private List<HikingSpot> parseHikingSpots(String response) {
        final List<HikingSpot> hikingSpots = new ArrayList<>();

        // Parse the response JSON
        final JSONObject responseJson = new JSONObject(response);
        final JSONArray results = responseJson.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            final JSONObject spotJson = results.getJSONObject(i);
            final String name = spotJson.getString("name");
            final double rating = spotJson.optDouble("rating", -1);
            final double latitude = spotJson.getJSONObject(GEOMETRY).getJSONObject(LOCATION).getDouble("lat");
            final double longitude = spotJson.getJSONObject(GEOMETRY).getJSONObject(LOCATION).getDouble("lng");
            final int totalratings = spotJson.optInt("user_ratings_total", -1);

            final HikingSpot spot = new HikingSpot(name, latitude, longitude, rating, totalratings);
            hikingSpots.add(spot);
        }

        return hikingSpots;
    }

    /**
     * A class that records the coordinates of the location that is entered by the users.
     * Its information is used for searching possible location for hiking.
     */
    public static class GeoLocation {
        private final double latitude;
        private final double longitude;

        public GeoLocation(double lat, double lng) {
            this.latitude = lat;
            this.longitude = lng;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

}
