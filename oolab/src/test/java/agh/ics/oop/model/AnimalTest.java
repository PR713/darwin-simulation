package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    @Test
    void testAnimalMove() {
        Animal animal = new Animal(new Vector2d(1, 1), MapDirection.N, 10, 1, 1, 5, 0, new Genome(4));
        animal.move(null, 3);
        assertEquals(new Vector2d(1, 2), animal.getPosition());

        animal.move(null, 7);
        assertEquals(new Vector2d(2, 1), animal.getPosition());

        animal.move(null, 0);
        assertEquals(new Vector2d(3, 1), animal.getPosition());
    }
}
