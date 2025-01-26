package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {


    @Test
    void reverseOrientationTest() {
        assertEquals(MapDirection.S, MapDirection.N.reverseOrientation());
        assertEquals(MapDirection.N, MapDirection.S.reverseOrientation());
        assertEquals(MapDirection.W, MapDirection.E.reverseOrientation());
        assertEquals(MapDirection.E, MapDirection.W.reverseOrientation());
        assertEquals(MapDirection.SW, MapDirection.NE.reverseOrientation());
        assertEquals(MapDirection.NE, MapDirection.SW.reverseOrientation());
        assertEquals(MapDirection.SE, MapDirection.NW.reverseOrientation());
        assertEquals(MapDirection.NW, MapDirection.SE.reverseOrientation());
    }


    @Test
    void fromNumericValueTest() {
        assertEquals(MapDirection.N, MapDirection.fromNumericValue(0));
        assertEquals(MapDirection.NE, MapDirection.fromNumericValue(1));
        assertEquals(MapDirection.E, MapDirection.fromNumericValue(2));
        assertEquals(MapDirection.SE, MapDirection.fromNumericValue(3));
        assertEquals(MapDirection.S, MapDirection.fromNumericValue(4));
        assertEquals(MapDirection.SW, MapDirection.fromNumericValue(5));
        assertEquals(MapDirection.W, MapDirection.fromNumericValue(6));
        assertEquals(MapDirection.NW, MapDirection.fromNumericValue(7));
    }


    @Test
    void fromNumericValueInvalidTest() {
        assertThrows(IllegalArgumentException.class, () -> MapDirection.fromNumericValue(-1));
        assertThrows(IllegalArgumentException.class, () -> MapDirection.fromNumericValue(8));
    }


    @Test
    void toMapDirectionVectorTest() {
        assertEquals(new Vector2d(0, 1), MapDirection.N.toMapDirectionVector());
        assertEquals(new Vector2d(0, -1), MapDirection.S.toMapDirectionVector());
        assertEquals(new Vector2d(-1, 0), MapDirection.W.toMapDirectionVector());
        assertEquals(new Vector2d(1, 0), MapDirection.E.toMapDirectionVector());
        assertEquals(new Vector2d(1, 1), MapDirection.NE.toMapDirectionVector());
        assertEquals(new Vector2d(1, -1), MapDirection.SE.toMapDirectionVector());
        assertEquals(new Vector2d(-1, -1), MapDirection.SW.toMapDirectionVector());
        assertEquals(new Vector2d(-1, 1), MapDirection.NW.toMapDirectionVector());
    }


    @Test
    void randomOrientationTest() {
        for (int i = 0; i < 50; i++) {
            MapDirection randomDirection = MapDirection.randomOrientation();
            assertNotNull(randomDirection);
            assertTrue(randomDirection.getNumericValue() >= 0 && randomDirection.getNumericValue() <= 7);
        }
    }


    @Test
    void toStringTest() {
        assertEquals("Północ", MapDirection.N.toString());
        assertEquals("Południe", MapDirection.S.toString());
        assertEquals("Zachód", MapDirection.W.toString());
        assertEquals("Wschód", MapDirection.E.toString());
        assertEquals("Północny wschód", MapDirection.NE.toString());
        assertEquals("Południowy wschód", MapDirection.SE.toString());
        assertEquals("Południowy zachód", MapDirection.SW.toString());
        assertEquals("Północny zachód", MapDirection.NW.toString());
    }
}
