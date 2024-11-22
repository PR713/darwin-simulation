package agh.ics.oop.model;

public class IncorrectPositionException extends Exception {

    public IncorrectPositionException(Vector2d position) {
        super(String.format("Position %s is not correct", position.toString()));
    }
}
