package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    //equals()
    @Test
    void equalsByTheSameValues() {
        Vector2d vector1 = new Vector2d(2,1);
        Vector2d vector2 = new Vector2d(2,1);

        assertTrue(vector1.equals(vector2));
    }

    @Test
    void equalsTwiceTheSameObject() {
        Vector2d vector = new Vector2d(1,5);

        assertTrue(vector.equals(vector));
    }

    @Test
    void equalsOnDifferentVectors() {
        Vector2d vector1 = new Vector2d(1,2);
        Vector2d vector2 = new Vector2d(3,4);

        assertFalse(vector1.equals(vector2));
    }

    @Test
    void equalsOnNullAndNotNull() {
        Vector2d vector1 = new Vector2d(1,1);
        Vector2d vector2 = null;

        assertFalse(vector1.equals(vector2));
    }


    //toString()
    @Test
    void toStringVector() {
        Vector2d vector = new Vector2d(1,2);

        assertEquals("(1, 2)",vector.toString());
    }

    //precedes
    //P2    P2     P2
    //P2    P1     P2 -> 8 + 1 (P1 = P2) -> 9 przypadków
    //P2    P2     P2
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

    //follows
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

    //upperRight
    @Test
    void upperRightOfTwoVectors() {
        Vector2d vector1 = new Vector2d(3,0);
        Vector2d vector2 = new Vector2d(3,4);

        assertEquals(new Vector2d(3, 4), vector1.upperRight(vector2));
    }

    //lowerLeft
    @Test
    void lowerLeftOfTwoVectors() {
        Vector2d vector1 = new Vector2d(1,7);
        Vector2d vector2 = new Vector2d(3,5);

        assertEquals(new Vector2d(1,5), vector1.lowerLeft(vector2));
    }


    //add
    @Test
    void addTwoVectors() {
        Vector2d vector1 = new Vector2d(1,7);
        Vector2d vector2 = new Vector2d(3,5);

        assertEquals(new Vector2d(4,12), vector1.add(vector2));
    }

    //subtract
    @Test
    void substractTwoVectors() {
        Vector2d vector1 = new Vector2d(1,7);
        Vector2d vector2 = new Vector2d(3,5);

        assertEquals(new Vector2d(-2,2), vector1.subtract(vector2));
    }

    //opposite
    @Test
    void oppositeVector() {
        Vector2d vector = new Vector2d(1,10);

        assertEquals(new Vector2d(-1,-10), vector.opposite());
    }
}
