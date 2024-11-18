import agh.ics.oop.World;
import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {


    @Test
    public void amountOfGrass() {
        GrassField map = new GrassField(10);
        assertEquals(10, map.getGrassTufts().size());
    }


    @Test
    public void noGrass() {
        GrassField map = new GrassField(0);
        assertEquals(0, map.getGrassTufts().size());
    }


    @Test
    public void testPlaceAnimalOnGrass() {
        GrassField map = new GrassField(6);

        Map<Vector2d, Grass> allGrassTufts = map.getGrassTufts();
        assertEquals(6, allGrassTufts.size());

        Vector2d occupiedPosition = allGrassTufts.keySet().iterator().next();
        Grass alreadyExisting = allGrassTufts.get(occupiedPosition);

        Animal animal = new Animal(occupiedPosition);
        map.place(animal);

        assertNotEquals(alreadyExisting, map.objectAt(occupiedPosition));
        assertEquals(animal, map.objectAt(occupiedPosition));
    }


    @Test
    public void testCanMoveTo() {
        GrassField map = new GrassField(5);

        Map<Vector2d, Grass> allGrassTufts = map.getGrassTufts();
        assertEquals(5, allGrassTufts.size());

        Vector2d vector = new Vector2d(1, 1);
        Vector2d vector1 = new Vector2d(1, 2);
        Animal animal = new Animal(vector);
        Animal animal1 = new Animal(vector1);
        map.place(animal);
        map.place(animal1);
        assertFalse(map.canMoveTo(vector));
    }


    @Test
    public void testGetElements() {
        GrassField map = new GrassField(2);
        Vector2d vector1 = new Vector2d(2, 2);
        Vector2d vector2 = new Vector2d(1, 2);
        Animal animal1 = new Animal(vector1);
        Animal animal2 = new Animal(vector2);

        map.place(animal1);
        map.place(animal2);

        List<WorldElement> result = map.getElements();
        List<WorldElement> expected = new ArrayList<>();
        Map<Vector2d, Grass> allGrassTufts = map.getGrassTufts();
        expected.add(animal1);
        expected.add(animal2);
        Iterator<Vector2d> iterator = allGrassTufts.keySet().iterator();
        expected.add(allGrassTufts.get(iterator.next()));
        expected.add(allGrassTufts.get(iterator.next()));

        assertEquals(expected.size(), result.size());

        for (WorldElement element : result) {
            assertTrue(expected.contains(element));
        }
    }


    @Test
    public void objectAtReturnsGrass() {
        GrassField map = new GrassField(10);

        Map<Vector2d, Grass> allGrass = map.getGrassTufts();

        for (Grass grass : allGrass.values()) {
            assertEquals(grass, map.objectAt(grass.getPosition()));
        }
    }


    @Test
    public void objectAtReturnsAnimalsAndGrass() {
        GrassField map = new GrassField(1);
        Vector2d vector = new Vector2d(1, 3);
        Vector2d vector1 = new Vector2d(2, 1);
        Vector2d vector2 = new Vector2d(0, 1);
        Animal animal = new Animal(vector);
        Animal animal1 = new Animal(vector1);
        Animal animal2 = new Animal(vector2);

        map.place(animal);
        map.place(animal1);
        map.place(animal2);

        List<WorldElement> allElements = map.getElements();

        for (WorldElement element : allElements) {
            if (element.getClass() == Grass.class && map.isOccupied(element.getPosition())) { //przysłania zwierzak
                assertNotEquals(element, map.objectAt(element.getPosition()));
            } else assertEquals(element, map.objectAt(element.getPosition())); //tylko zwierzak lub tylko trawa
        }
    }
}
