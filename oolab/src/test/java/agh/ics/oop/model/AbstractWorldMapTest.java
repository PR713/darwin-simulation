package agh.ics.oop.model;

import agh.ics.oop.exceptions.IncorrectPositionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class AbstractWorldMapTest {

    @Test
    public void testGetOrderedAnimals() throws IncorrectPositionException {
        AbstractWorldMap map = new GrassField(10);


        Animal animal1 = new Animal(new Vector2d(2, 3));
        Animal animal2 = new Animal(new Vector2d(1, 2));
        Animal animal3 = new Animal(new Vector2d(2, 1));
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        List<Animal> orderedAnimals = map.getOrderedAnimals();
        List<Animal> correctlyOrderedAnimals = List.of(animal2, animal3, animal1);
        assertEquals(orderedAnimals, correctlyOrderedAnimals);
    }
}