//package agh.ics.oop.model;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class AnimalTest {
//
//    @Test
//    void correctPositionDefault() {
//        AbstractAnimal animal = new AbstractAnimal();
//
//        assertEquals(new Vector2d(2,2), animal.getPosition());
//    }
//
//    @Test
//    void correctPositionSet() {
//        Vector2d position = new Vector2d(1,3);
//        AbstractAnimal animal = new AbstractAnimal(position);
//
//        assertEquals(new Vector2d(1,3), animal.getPosition());
//    }
//
//    @Test
//    void correctPositionSetOnEdges() {
//        Vector2d position1 = new Vector2d(0,0);
//        Vector2d position2 = new Vector2d(4,4);
//        Vector2d position3 = new Vector2d(0,4);
//        Vector2d position4 = new Vector2d(4,0);
//
//        AbstractAnimal animal1 = new AbstractAnimal(position1);
//        AbstractAnimal animal2 = new AbstractAnimal(position2);
//        AbstractAnimal animal3 = new AbstractAnimal(position3);
//        AbstractAnimal animal4 = new AbstractAnimal(position4);
//
//        assertEquals(position1, animal1.getPosition());
//        assertEquals(position2, animal2.getPosition());
//        assertEquals(position3, animal3.getPosition());
//        assertEquals(position4, animal4.getPosition());
//    }
//
//    @Test
//    void defaultOrientation() {
//        AbstractAnimal animal = new AbstractAnimal();
//
//        assertEquals(MapDirection.NORTH, animal.getOrientation());
//    }
//
//    @Test
//    void moveAllDirections(){
//        AbstractAnimal animal1 = new AbstractAnimal(new Vector2d(2,3));
//        AbstractAnimal animal2 = new AbstractAnimal(new Vector2d(2,3));
//        AbstractAnimal animal3 = new AbstractAnimal(new Vector2d(2,3));
//        AbstractAnimal animal4 = new AbstractAnimal(new Vector2d(2,3));
//        WorldMap map = new RectangularMap(5,5);
//        animal1.move(map,MoveDirection.LEFT);
//        animal2.move(map,MoveDirection.RIGHT);
//        animal3.move(map,MoveDirection.FORWARD);
//        animal4.move(map,MoveDirection.BACKWARD);
//
//
//        assertEquals(MapDirection.WEST, animal1.getOrientation());
//        assertEquals(MapDirection.EAST, animal2.getOrientation());
//
//        assertEquals(MapDirection.NORTH, animal3.getOrientation());
//        assertEquals(new Vector2d(2,4), animal3.getPosition());
//
//        assertEquals(MapDirection.NORTH, animal4.getOrientation());
//        assertEquals(new Vector2d(2,2), animal4.getPosition());
//    }
//
//    @Test
//    void isAt() {
//        Vector2d position = new Vector2d(2,3);
//        AbstractAnimal animal = new AbstractAnimal(position);
//
//        assertTrue(animal.isAt(position));
//    }
//
//    @Test
//    void isNotAt() {
//        Vector2d position = new Vector2d(0,1);
//        AbstractAnimal animal = new AbstractAnimal(new Vector2d(2,4));
//
//        assertFalse(animal.isAt(position));
//    }
//
//    @Test
//    void toStringAnimal() {
//        AbstractAnimal animal = new AbstractAnimal(new Vector2d(2,3));
//
//        assertEquals("^", animal.toString());
//    }
//
//}
