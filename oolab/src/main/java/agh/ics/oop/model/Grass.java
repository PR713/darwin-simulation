package agh.ics.oop.model;

import javafx.scene.paint.Color;

public class Grass implements WorldElement {
    private final Vector2d tuftPosition;

    public Grass(Vector2d tuftPosition){
        this.tuftPosition = tuftPosition;
    }


    @Override
    public Vector2d getPosition(){
        return this.tuftPosition;
    }


    @Override
    public String toString() {
        return "*";
    }

    @Override
    public Color getColor() {
        return null;
    }
}
