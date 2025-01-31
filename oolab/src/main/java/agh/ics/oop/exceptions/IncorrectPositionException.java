package agh.ics.oop.exceptions; // samo bycie wyjątkiem, to nie jest cecha wspólna, która usprawiedliwia wspólny pakiet

import agh.ics.oop.model.Vector2d;

public class IncorrectPositionException extends Exception {

    public IncorrectPositionException(Vector2d position) {
        super(String.format("Position %s is not correct", position));
    }
}
