import agh.ics.oop.model.RectangularMap;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import org.junit.jupiter.api.Test;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    @Test
    public void placingAnimalsOnMap(){
        WorldMap map = new RectangularMap(5,5);
        Vector2d vector1 = new Vector2d(2,2);
        Vector2d vector2 = new Vector2d(1,3);
        Animal animal1 = new Animal(vector1);
        Animal animal2 = new Animal(vector2);

        assertTrue(map.place(animal1));
        assertTrue(map.place(animal2));
        assertTrue(map.isOccupied(vector1));
        assertTrue(map.isOccupied(vector2));
        assertEquals(animal1, map.objectAt(vector1));
        //nie ma nadpisanego Equals w Animal, więc porównuje referencję
        //map.objectAt zwraca obiekt Animal więc ok
        assertEquals(animal2, map.objectAt(vector2));
    }


    @Test
    public void placingAnimalsOnMapWithTheSamePositions(){
        WorldMap map = new RectangularMap(5,5);
        Vector2d vector = new Vector2d(2,2);
        Animal animal1 = new Animal(vector);
        Animal animal2 = new Animal(vector);

        assertTrue(map.place(animal1));
        assertFalse(map.place(animal2));
        assertTrue(map.isOccupied(vector));

        assertEquals(animal1, map.objectAt(vector));
        assertNotEquals(animal2, map.objectAt(vector));
    }


    @Test
    public void moveAnimalsWithChangingOrienationsOrPositions(){
        WorldMap map = new RectangularMap(7,7);
        Vector2d vector1 = new Vector2d(2,3);
        Animal animal1 = new Animal(vector1);
        Vector2d vector2 = new Vector2d(1,2);
        Animal animal2 = new Animal(vector2);
        Vector2d vector3 = new Vector2d(0,2);
        Animal animal3 = new Animal(vector3);
        Vector2d vector4 = new Vector2d(2,4);
        Animal animal4 = new Animal(vector4);

        animal1.move(map, MoveDirection.FORWARD);
        animal2.move(map, MoveDirection.BACKWARD);
        animal3.move(map, MoveDirection.LEFT);
        animal4.move(map, MoveDirection.RIGHT);

        assertEquals(new Vector2d(2,4),animal1.getPosition());
        assertEquals(MapDirection.NORTH,animal1.getOrientation());

        assertEquals(new Vector2d(1,1),animal2.getPosition());
        assertEquals(MapDirection.NORTH,animal2.getOrientation());

        assertEquals(vector3, animal3.getPosition());
        assertEquals(MapDirection.WEST,animal3.getOrientation());

        assertEquals(vector4, animal4.getPosition());
        assertEquals(MapDirection.EAST,animal4.getOrientation());
    }

    @Test
    public void canMoveTo(){
        WorldMap map = new RectangularMap(3,3);
        Vector2d vector1 = new Vector2d(1,2);
        Animal animal1 = new Animal(vector1);
        Vector2d vector2 = new Vector2d(3,2);
        Animal animal2 = new Animal(vector2);

        animal1.move(map, MoveDirection.FORWARD);
        animal2.move(map, MoveDirection.RIGHT);
        animal2.move(map, MoveDirection.FORWARD);

        assertTrue(animal1.isAt(vector1));
        assertTrue(animal2.isAt(vector2));
    }



    @Test
    public void elementsRectangularMap(){
        WorldMap map = new RectangularMap(4,4);
        Vector2d vector1 = new Vector2d(1,3);
        Animal animal1 = new Animal(vector1);
        Vector2d vector2 = new Vector2d(3,2);
        Animal animal2 = new Animal(vector2);

        map.place(animal1);
        map.place(animal2);
        Map<Vector2d, WorldElement> result = new HashMap<>();
        result.put(vector1, animal1);
        result.put(vector2, animal2);

        Map<Vector2d, WorldElement> elements = map.getElements();

        assertEquals(result, elements);
    }
}
