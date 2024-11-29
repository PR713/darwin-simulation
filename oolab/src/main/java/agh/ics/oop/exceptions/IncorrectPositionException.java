package agh.ics.oop.exceptions;

import agh.ics.oop.model.Vector2d;

public class IncorrectPositionException extends Exception {

    public IncorrectPositionException(Vector2d position) {
        super(String.format("Position %s is not correct", position));
    }
}
