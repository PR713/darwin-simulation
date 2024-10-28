import agh.ics.oop.model.MapDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {
    //next()
    @Test
    void nextFromNorth() {
        //given
        MapDirection map = MapDirection.NORTH;
        //when
        map = map.next();
        //then
        assertEquals(MapDirection.EAST, map);
    }

    @Test
    void nextFromEast() {
        //given
        MapDirection map = MapDirection.EAST;
        //when
        map = map.next();
        //then
        assertEquals(MapDirection.SOUTH, map);
    }

    @Test
    void nextFromSouth() {
        //given
        MapDirection map = MapDirection.SOUTH;
        //when
        map = map.next();
        //then
        assertEquals(MapDirection.WEST, map);
    }

    @Test
    void nextFromWest() {
        //given
        MapDirection map = MapDirection.WEST;
        //when
        map = map.next();
        //then
        assertEquals(MapDirection.NORTH, map);
    }

    //previous

    @Test
    void previousFromNorth() {
        // given
        MapDirection map = MapDirection.NORTH;
        // when
        map = map.previous();
        // then
        assertEquals(MapDirection.WEST, map);
    }

    @Test
    void previousFromEast() {
        // given
        MapDirection map = MapDirection.EAST;
        // when
        map = map.previous();
        // then
        assertEquals(MapDirection.NORTH, map);
    }

    @Test
    void previousFromSouth() {
        // given
        MapDirection map = MapDirection.SOUTH;
        // when
        map = map.previous();
        // then
        assertEquals(MapDirection.EAST, map);
    }

    @Test
    void previousFromWest() {
        // given
        MapDirection map = MapDirection.WEST;
        // when
        map = map.previous();
        // then
        assertEquals(MapDirection.SOUTH, map);
    }




}
