package backend.service;

import frontend.utils.HttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationService {
    private static final String GOOGLE_API_KEY = "To be modified later";

    /**
     * Get the geographical coordinates (latitude and longitude) for a given location.
     *
     * @param location the location to get coordinates for
     * @return a GeoLocation object containing latitude and longitude
     * @throws IllegalArgumentException if the location is not valid
     */
    public GeoLocation getCoordinates(String location) {
        String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + location.replace(" ", "+") + "&key=" + GOOGLE_API_KEY;

        // Use the HttpClient utility to send a GET request
        String response = HttpClient.sendGetRequest(apiUrl);

        // Parse the response JSON
        JSONObject responseJson = new JSONObject(response);
        JSONArray results = responseJson.getJSONArray("results");

        if (results.length() > 0) {
            JSONObject locationJson = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            double latitude = locationJson.getDouble("lat");
            double longitude = locationJson.getDouble("lng");
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
        String apiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                geoLocation.getLatitude() + "," + geoLocation.getLongitude() +
                "&radius=5000&keyword=hiking+trail&key=" + GOOGLE_API_KEY;

        // Use the HttpClient utility to send a GET request
        String response = HttpClient.sendGetRequest(apiUrl);

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
        List<HikingSpot> hikingSpots = new ArrayList<>();

        // Parse the response JSON
        JSONObject responseJson = new JSONObject(response);
        JSONArray results = responseJson.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject spotJson = results.getJSONObject(i);
            String name = spotJson.getString("name");
            double rating = spotJson.optDouble("rating", -1); // -1 means no rating
            String vicinity = spotJson.getString("vicinity");
            double latitude = spotJson.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
            double longitude = spotJson.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
            int usersrating = spotJson.optInt("user_ratings_total",-1);

            HikingSpot spot = new HikingSpot(name, latitude, longitude, rating, usersrating);
            hikingSpots.add(spot);
        }

        return hikingSpots;
    }

    public static class GeoLocation {
        private double Latitude;
        private double Longitude;

        public GeoLocation(double lat, double lng) {
            this.Latitude = lat;
            this.Longitude = lng;
        }

        public double getLatitude() { return Latitude; }

        public double getLongitude() { return Longitude; }
    }

    public static class HikingSpot {
        private String name;
        private double latitude;
        private double longitude;
        private Double rating; // Rating of the hiking spot (nullable)
        private Integer userRatingsTotal; // Total number of user ratings

        public HikingSpot(String name, double latitude, double longitude, Double rating, Integer userRatingsTotal) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.rating = rating;
            this.userRatingsTotal = userRatingsTotal;
        }

        // Getters
        public String getName() { return name; }
        public double getLatitude() { return latitude; }
        public double getLongitude() { return longitude; }
        public Double getRating() { return rating; }
        public Integer getUserRatingsTotal() { return userRatingsTotal; }

        @Override
        public String toString() {
            return "HikingSpot{name='" + name + "', latitude=" + latitude + ", longitude=" + longitude +
                    ", rating=" + rating + ", userRatingsTotal=" + userRatingsTotal + "}";
        }
    }
}

