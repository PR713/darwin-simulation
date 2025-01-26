package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {


    @Test
    void equalsByTheSameValues() {
        Vector2d vector1 = new Vector2d(2,1);
        Vector2d vector2 = new Vector2d(2,1);

        assertEquals(vector1, vector2);
    }


    @Test
    void equalsTwiceTheSameObject() {
        Vector2d vector = new Vector2d(1,5);

        assertEquals(vector, vector);
    }


    @Test
    void equalsOnDifferentVectors() {
        Vector2d vector1 = new Vector2d(1,2);
        Vector2d vector2 = new Vector2d(3,4);

        assertNotEquals(vector1, vector2);
    }


    @Test
    void equalsOnNullAndNotNull() {
        Vector2d vector1 = new Vector2d(1,1);
        Vector2d vector2 = null;

        assertNotEquals(vector1, vector2);
    }



    @Test
    void toStringVector() {
        Vector2d vector = new Vector2d(1,2);

        assertEquals("(1, 2)",vector.toString());
    }


    @Test
    void precedesTwoVectorsTrue() {
        Vector2d vector1 = new Vector2d(3,2);
        Vector2d vector2 = new Vector2d(3,4);

        assertTrue(vector1.precedes(vector2));
    }


    @Test
    void precedesTwoVectorsFalse() {
        Vector2d vector1 = new Vector2d(3,10);
        Vector2d vector2 = new Vector2d(3,4);

        assertFalse(vector1.precedes(vector2));
    }


    @Test
    void followsTwoVectorsTrue(){
        Vector2d vector1 = new Vector2d(3,10);
        Vector2d vector2 = new Vector2d(3,4);

        assertTrue(vector1.follows(vector2));
    }


    @Test
    void followsTwoVectorsFalse(){
        Vector2d vector1 = new Vector2d(3,0);
        Vector2d vector2 = new Vector2d(3,4);

        assertFalse(vector1.follows(vector2));
    }


    @Test
    void upperRightOfTwoVectors() {
        Vector2d vector1 = new Vector2d(3,0);
        Vector2d vector2 = new Vector2d(3,4);

        assertEquals(new Vector2d(3, 4), vector1.upperRight(vector2));
    }


    @Test
    void lowerLeftOfTwoVectors() {
        Vector2d vector1 = new Vector2d(1,7);
        Vector2d vector2 = new Vector2d(3,5);

        assertEquals(new Vector2d(1,5), vector1.lowerLeft(vector2));
    }


    @Test
    void addTwoVectors() {
        Vector2d vector1 = new Vector2d(1,7);
        Vector2d vector2 = new Vector2d(3,5);

        assertEquals(new Vector2d(4,12), vector1.add(vector2));
    }


    @Test
    void subtractTwoVectors() {
        Vector2d vector1 = new Vector2d(1,7);
        Vector2d vector2 = new Vector2d(3,5);

        assertEquals(new Vector2d(-2,2), vector1.subtract(vector2));
    }


    @Test
    void oppositeVector() {
        Vector2d vector = new Vector2d(1,10);

        assertEquals(new Vector2d(-1,-10), vector.opposite());
    }
}
