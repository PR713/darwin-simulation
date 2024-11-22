package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {
    //next()
    @Test
    void nextFromNorth() {
        MapDirection map = MapDirection.NORTH;

        map = map.next();

        assertEquals(MapDirection.EAST, map);
    }

    @Test
    void nextFromEast() {
        MapDirection map = MapDirection.EAST;

        map = map.next();

        assertEquals(MapDirection.SOUTH, map);
    }

    @Test
    void nextFromSouth() {
        MapDirection map = MapDirection.SOUTH;

        map = map.next();

        assertEquals(MapDirection.WEST, map);
    }

    @Test
    void nextFromWest() {
        MapDirection map = MapDirection.WEST;

        map = map.next();

        assertEquals(MapDirection.NORTH, map);
    }

    //previous

    @Test
    void previousFromNorth() {
        MapDirection map = MapDirection.NORTH;

        map = map.previous();

        assertEquals(MapDirection.WEST, map);
    }

    @Test
    void previousFromEast() {
        MapDirection map = MapDirection.EAST;

        map = map.previous();

        assertEquals(MapDirection.NORTH, map);
    }

    @Test
    void previousFromSouth() {
        MapDirection map = MapDirection.SOUTH;

        map = map.previous();

        assertEquals(MapDirection.EAST, map);
    }

    @Test
    void previousFromWest() {
        MapDirection map = MapDirection.WEST;

        map = map.previous();

        assertEquals(MapDirection.SOUTH, map);
    }
}
