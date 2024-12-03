package entity;

/**
 * Represents a recommended hiking spot that is near the location entered by the user.
 * Records the information of this hiking spot.
 */
public class HikingSpot {
    private final String name;
    private final double latitude;
    private final double longitude;
    private final Double rating;
    private final Integer userRatingsTotal;

    public HikingSpot(String name, double latitude, double longitude, Double rating, Integer userRatingsTotal) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.userRatingsTotal = userRatingsTotal;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getUserRatingsTotal() {
        return userRatingsTotal;
    }

    @Override
    public String toString() {
        return "HikingSpot{name='" + name + "', latitude=" + latitude + ", longitude=" + longitude
                + ", rating=" + rating + ", userRatingsTotal=" + userRatingsTotal + "}";
    }
}

