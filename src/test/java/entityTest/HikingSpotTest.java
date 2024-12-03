package entityTest;

import entity.HikingSpot;
import org.junit.Test;
import static org.junit.Assert.*;

public class HikingSpotTest {

    @Test
    public void testHikingSpotConstructor() {
        HikingSpot spot = new HikingSpot("Central Park", 40.785091, -73.968285, 4.8, 2500);
        assertEquals("Central Park", spot.getName());
        assertEquals(40.785091, spot.getLatitude(), 0.0001);
        assertEquals(-73.968285, spot.getLongitude(), 0.0001);
        assertEquals(4.8, spot.getRating(), 0.0001);
        assertEquals(2500, (int) spot.getUserRatingsTotal());
    }

    @Test
    public void testHikingSpotInequality() {
        HikingSpot spot1 = new HikingSpot("Central Park", 40.785091, -73.968285, 4.8, 2500);
        HikingSpot spot2 = new HikingSpot("Brooklyn Bridge Park", 40.700291, -73.996691, 4.7, 1500);
        assertNotEquals(spot1, spot2);
    }

    @Test
    public void testHikingSpotToString() {
        HikingSpot spot = new HikingSpot("Central Park", 40.785091, -73.968285, 4.8, 2500);
        String expected = "HikingSpot{name='Central Park', latitude=40.785091, longitude=-73.968285, rating=4.8, userRatingsTotal=2500}";
        assertEquals(expected, spot.toString());
    }


}