import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import agh.ics.oop.model.AbstractWorldMap;

public class GrassFieldTest {

    @Test
    public void testPlaceGrass() {
        GrassField map = new GrassField(0);
        Vector2d vector = new Vector2d(0,0);
        Grass grass = new Grass(vector);

        assertTrue(map.place(grass));
        assertTrue(map.isOccupied(vector));

        assertEquals(grass, map.objectAt(grass.getPosition()));
    }


    @Test
    public void testPlacingGrassWhichIsOccupiedByAnotherGrass() {
        GrassField map = new GrassField(3);
        Map<Vector2d, Grass> allGrassTufts = map.getGrassTufts();

        assertEquals(3, allGrassTufts.size());

        Vector2d occupiedPosition = allGrassTufts.keySet().iterator().next();
        Grass alreadyExisting = allGrassTufts.get(occupiedPosition);
        Grass grassTest = new Grass(occupiedPosition);
        assertNotEquals(grassTest, map.objectAt(occupiedPosition));
        assertEquals(alreadyExisting, map.objectAt(occupiedPosition));
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

        Vector2d vector = new Vector2d(1,1);
        Vector2d vector1 = new Vector2d(1,2);
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
        expected.add(allGrassTufts.get(allGrassTufts.keySet().iterator().next()));
        expected.add(allGrassTufts.get(allGrassTufts.keySet().iterator().next()));

        assertEquals(expected.size(), result.size());
        System.out.println(expected);
        System.out.println(result);
        for ( WorldElement element : result){
            assertTrue(expected.contains(element));
        }
    }
}
