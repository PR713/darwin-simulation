import agh.ics.oop.OptionsParser;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    @Test
    void correctPositionDefault() {
        Animal animal = new Animal();

        assertEquals(new Vector2d(2,2), animal.position);
    }

    @Test
    void correctPositionSet() {
        Animal animal = new Animal(new Vector2d(1,3));

        assertEquals(new Vector2d(1,3), animal.position);
    }

    @Test
    void correctPositionSetOnEdges() {
        Animal animal1 = new Animal(new Vector2d(0,0));
        Animal animal2 = new Animal(new Vector2d(4,4));
        Animal animal3 = new Animal(new Vector2d(0,4));
        Animal animal4 = new Animal(new Vector2d(4,0));

        assertEquals(new Vector2d(0,0), animal1.position);
        assertEquals(new Vector2d(4,4), animal2.position);
        assertEquals(new Vector2d(0,4), animal3.position);
        assertEquals(new Vector2d(4,0), animal4.position);
    }

    @Test
    void defaultOrientation() {
        Animal animal = new Animal();

        assertEquals(MapDirection.NORTH, animal.orientation);
    }

    @Test
    void moveAllDirections(){
        Animal animal1 = new Animal(new Vector2d(2,3));
        Animal animal2 = new Animal(new Vector2d(2,3));
        Animal animal3 = new Animal(new Vector2d(2,3));
        Animal animal4 = new Animal(new Vector2d(2,3));
        animal1.move(MoveDirection.LEFT);
        animal2.move(MoveDirection.RIGHT);
        animal3.move(MoveDirection.FORWARD);
        animal4.move(MoveDirection.BACKWARD);


        assertEquals(MapDirection.WEST, animal1.orientation);
        assertEquals(MapDirection.EAST, animal2.orientation);

        assertEquals(MapDirection.NORTH, animal3.orientation);
        assertEquals(new Vector2d(2,4), animal3.position);

        assertEquals(MapDirection.NORTH, animal4.orientation);
        assertEquals(new Vector2d(2,2), animal4.position);
    }

    @Test
    void isAt() {
        Vector2d position = new Vector2d(2,3);
        Animal animal = new Animal(position);

        assertTrue(animal.isAt(position));
    }

    @Test
    void isNotAt() {
        Vector2d position = new Vector2d(0,1);
        Animal animal = new Animal(new Vector2d(2,4));

        assertFalse(animal.isAt(position));
    }
}
