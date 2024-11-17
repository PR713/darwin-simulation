import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        GrassField map = new GrassField(2);
        exampleGrass = map.grassTufts
        Vector2d vector = new Vector2d(1,2);//poza obszarem gdzie normalnie mogą być trawy
        Grass grass1 = new Grass(vector);
        Grass grass2 = new Grass(vector);

        assertTrue(map.place(grass1));
        assertFalse(map.place(grass2));

        assertEquals(grass1, map.objectAt(grass1.getPosition()));
        assertNotEquals(grass2, map.objectAt(grass1.getPosition()));
    }


    @Test
    public void testPlaceAnimalOnGrass() {
        GrassField map = new GrassField(6);
        Vector2d vector = new Vector2d(1,2);
        Grass grass = new Grass(vector);
        Animal animal = new Animal(vector);

        map.place(grass); // wywowałane na metodzie z GrassField
        map.place(animal); //wywołane z super
        System.out.println(map.getElements());
        assertEquals(animal, map.objectAt(vector));
    }


    @Test
    public void testCanMoveTo() {
        GrassField map = new GrassField(6);
        Vector2d vector = new Vector2d(3, 2);
        Vector2d vector1 = new Vector2d(2, 2);
        Grass grass = new Grass(vector);
        Animal animal = new Animal(vector1);

        map.place(grass);
        map.place(animal);

        assertTrue(map.canMoveTo(vector));
    }


    @Test
    public void testGetElements() {
        GrassField map = new GrassField(1);
        Vector2d vector = new Vector2d(3, 2);
        Vector2d vector1 = new Vector2d(2, 2);
        Vector2d vector2 = new Vector2d(1, 2);
        Grass grass = new Grass(vector);
        Animal animal1 = new Animal(vector1);
        Animal animal2 = new Animal(vector2);

        map.place(animal1);
        map.place(animal2);
        map.place(grass);
        List<WorldElement> result = map.getElements();
        List<WorldElement> expected = new ArrayList<>();
        expected.add(grass);
        expected.add(animal1);
        expected.add(animal2);
        System.out.println(result);
        assertEquals(expected.size(), result.size());
        for ( WorldElement element : result){
            assertTrue(expected.contains(element));
        }
    }
}
