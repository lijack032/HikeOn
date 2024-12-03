package entityTest;

import entity.GeoLocation;
import org.junit.Test;
import static org.junit.Assert.*;

public class GeoLocationTest {

    @Test
    public void testGeoLocationConstructor() {
        GeoLocation location = new GeoLocation(40.7128, -74.0060);
        assertEquals(40.7128, location.getLatitude(), 0.0001);
        assertEquals(-74.0060, location.getLongitude(), 0.0001);
    }

    @Test
    public void testGeoLocationInequality() {
        GeoLocation location1 = new GeoLocation(40.7128, -74.0060);
        GeoLocation location2 = new GeoLocation(34.0522, -118.2437);
        assertNotEquals(location1, location2);
    }

}