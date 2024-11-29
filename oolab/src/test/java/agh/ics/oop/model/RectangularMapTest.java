package agh.ics.oop.model;

import agh.ics.oop.exceptions.IncorrectPositionException;
import org.junit.jupiter.api.Test;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    @Test
    public void placingAnimalsOnMap() {
        RectangularMap map = new RectangularMap(5, 5);
        Vector2d vector1 = new Vector2d(2, 2);
        Vector2d vector2 = new Vector2d(1, 3);
        Animal animal1 = new Animal(vector1);
        Animal animal2 = new Animal(vector2);

        assertDoesNotThrow(() -> map.place(animal1));
        assertDoesNotThrow(() -> map.place(animal2));

        assertTrue(map.isOccupied(vector1));
        assertTrue(map.isOccupied(vector2));
        assertEquals(animal1, map.objectAt(vector1));
        //nie ma nadpisanego Equals w Animal, więc porównuje referencję
        //map.objectAt zwraca obiekt Animal więc ok
        assertEquals(animal2, map.objectAt(vector2));
    }


    @Test
    public void placingAnimalsOnMapWithTheSamePositions() {
        RectangularMap map = new RectangularMap(5, 5);
        Vector2d vector = new Vector2d(2, 2);
        Animal animal1 = new Animal(vector);
        Animal animal2 = new Animal(vector);

        assertDoesNotThrow(() -> map.place(animal1));
        //try { lub tak
        //    map.place(animal1);
        //} catch (IncorrectPositionException e) {
        //    fail("Expected no exception, but got: " + e);
        //} a oczekujemy że nie ma co łapać

        IncorrectPositionException exception = assertThrows(
                IncorrectPositionException.class,
                () -> map.place(animal2)
        );

        assertEquals("Position (2, 2) is not correct", exception.getMessage());
        assertTrue(map.isOccupied(vector));
        assertEquals(animal1, map.objectAt(vector));
    }


    @Test
    public void moveAnimalsWithChangingOrientationsOrPositions() {
        RectangularMap map = new RectangularMap(7, 7);
        Vector2d vector1 = new Vector2d(2, 3);
        Animal animal1 = new Animal(vector1);
        Vector2d vector2 = new Vector2d(1, 2);
        Animal animal2 = new Animal(vector2);
        Vector2d vector3 = new Vector2d(0, 2);
        Animal animal3 = new Animal(vector3);
        Vector2d vector4 = new Vector2d(2, 4);
        Animal animal4 = new Animal(vector4);

        animal1.move(map, MoveDirection.FORWARD);
        animal2.move(map, MoveDirection.BACKWARD);
        animal3.move(map, MoveDirection.LEFT);
        animal4.move(map, MoveDirection.RIGHT);

        assertEquals(new Vector2d(2, 4), animal1.getPosition());
        assertEquals(MapDirection.NORTH, animal1.getOrientation());

        assertEquals(new Vector2d(1, 1), animal2.getPosition());
        assertEquals(MapDirection.NORTH, animal2.getOrientation());

        assertEquals(vector3, animal3.getPosition());
        assertEquals(MapDirection.WEST, animal3.getOrientation());

        assertEquals(vector4, animal4.getPosition());
        assertEquals(MapDirection.EAST, animal4.getOrientation());
    }

    @Test
    public void canMoveTo() {
        RectangularMap map = new RectangularMap(3, 3);
        Vector2d vector1 = new Vector2d(1, 2);
        Animal animal1 = new Animal(vector1);
        Vector2d vector2 = new Vector2d(3, 2);
        Animal animal2 = new Animal(vector2);

        animal1.move(map, MoveDirection.FORWARD);
        animal2.move(map, MoveDirection.RIGHT);
        animal2.move(map, MoveDirection.FORWARD);

        assertTrue(animal1.isAt(vector1));
        assertTrue(animal2.isAt(vector2));
    }


    @Test
    public void elementsRectangularMap() {
        RectangularMap map = new RectangularMap(4, 4);
        Vector2d vector1 = new Vector2d(1, 3);
        Animal animal1 = new Animal(vector1);
        Vector2d vector2 = new Vector2d(3, 2);
        Animal animal2 = new Animal(vector2);

        assertDoesNotThrow(() -> map.place(animal1));
        assertDoesNotThrow(() -> map.place(animal2));

        List<WorldElement> result = map.getElements();
        List<WorldElement> expected = new ArrayList<>();
        expected.add(animal1);
        expected.add(animal2);


        assertEquals(expected.size(), result.size());
        for (WorldElement element : result) {
            assertTrue(expected.contains(element));
        }
    }


    @Test
    public void elementsButSameRectangularMap() {
        RectangularMap map = new RectangularMap(4, 4);
        Vector2d vector1 = new Vector2d(1, 3);
        Animal animal1 = new Animal(vector1);
        Vector2d vector2 = new Vector2d(3, 2);
        Animal animal2 = new Animal(vector2);
        Vector2d vector3 = new Vector2d(3, 2);
        Animal animal3 = new Animal(vector3);

        assertDoesNotThrow(() -> map.place(animal1));
        assertDoesNotThrow(() -> map.place(animal2));

        IncorrectPositionException exception3 = assertThrows(
                IncorrectPositionException.class,
                () -> map.place(animal3)
        );

        assertEquals("Position (3, 2) is not correct", exception3.getMessage());
        List<WorldElement> result = map.getElements();
        List<WorldElement> expected = new ArrayList<>();
        expected.add(animal1);
        expected.add(animal2);
        expected.add(animal3);


        assertNotEquals(expected.size(), result.size());
    }

    @Test
    public void getElementsButOnlyAnimals() {
        RectangularMap map = new RectangularMap(5, 5);
        Vector2d vector1 = new Vector2d(2, 4);
        Vector2d vector2 = new Vector2d(1, 2);
        Animal animal1 = new Animal(vector1);
        Animal animal2 = new Animal(vector2);

        assertDoesNotThrow(() -> map.place(animal1));
        assertDoesNotThrow(() -> map.place(animal2));

        List<WorldElement> allAnimals = map.getElements();

        List<WorldElement> expected = new ArrayList<>();
        expected.add(animal1);
        expected.add(animal2);

        assertEquals(expected.size(), allAnimals.size());

        for (WorldElement animal : allAnimals) {
            assertTrue(expected.contains(animal));
        }
    }
}
