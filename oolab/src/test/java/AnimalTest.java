
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    @Test
    void correctPositionDefault() {
        Animal animal = new Animal();

        assertEquals(new Vector2d(2,2), animal.getPosition());
    }

    @Test
    void correctPositionSet() {
        Vector2d position = new Vector2d(1,3);
        Animal animal = new Animal(position);

        assertEquals(new Vector2d(1,3), animal.getPosition());
    }

    @Test
    void correctPositionSetOnEdges() {
        Vector2d position1 = new Vector2d(0,0);
        Vector2d position2 = new Vector2d(4,4);
        Vector2d position3 = new Vector2d(0,4);
        Vector2d position4 = new Vector2d(4,0);

        Animal animal1 = new Animal(position1);
        Animal animal2 = new Animal(position2);
        Animal animal3 = new Animal(position3);
        Animal animal4 = new Animal(position4);

        assertEquals(position1, animal1.getPosition());
        assertEquals(position2, animal2.getPosition());
        assertEquals(position3, animal3.getPosition());
        assertEquals(position4, animal4.getPosition());
    }

    @Test
    void defaultOrientation() {
        Animal animal = new Animal();

        assertEquals(MapDirection.NORTH, animal.getOrientation());
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


        assertEquals(MapDirection.WEST, animal1.getOrientation());
        assertEquals(MapDirection.EAST, animal2.getOrientation());

        assertEquals(MapDirection.NORTH, animal3.getOrientation());
        assertEquals(new Vector2d(2,4), animal3.getPosition());

        assertEquals(MapDirection.NORTH, animal4.getOrientation());
        assertEquals(new Vector2d(2,2), animal4.getPosition());
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

    @Test
    void toStringAnimal() {
        Animal animal = new Animal(new Vector2d(2,3));

        assertEquals("(2, 3) , Północ", animal.toString());
    }

}
