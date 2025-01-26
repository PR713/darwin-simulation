package agh.ics.oop.model;

import javafx.scene.paint.Color;

public interface WorldElement {
    Vector2d getPosition();

    String toString();

    Color getColor();
}
