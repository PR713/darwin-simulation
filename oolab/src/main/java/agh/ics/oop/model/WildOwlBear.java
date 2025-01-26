package agh.ics.oop.model;

import javafx.scene.paint.Color;

import static agh.ics.oop.model.MapDirection.fromNumericValue;

public class WildOwlBear extends AbstractAnimal {

    private int animalsEaten = 0;

    public WildOwlBear(Vector2d position, MapDirection orientation, int genomeLength, int startIndexOfGenome, Genome genome) {
        super(position, orientation, genomeLength, startIndexOfGenome, genome);
    }


    @Override
    public void move(MoveValidator validator, int direction) {
        WildOwlBearMap map = (WildOwlBearMap) validator;
        MapDirection newOrientation = fromNumericValue((this.orientation.getNumericValue() + direction) % 8);
        Vector2d newPosition = this.position.add(this.orientation.toMapDirectionVector());

        if (map.canMoveToOwl(newPosition)) {
            this.position = newPosition;
            this.orientation = newOrientation;
        } else {
            if (map.isOwlBearMovingBeyondBordersHorizontally(newPosition)
                    && map.isOwlBearMovingBeyondBordersVertically(newPosition)) {
                //position doesn't change
                this.orientation = newOrientation.reverseOrientation();

            } else if (map.isOwlBearMovingBeyondBordersHorizontally(newPosition)) {
                this.orientation = newOrientation.reverseOrientation();
            } else { // vertically but not in corners, position doesn't change
                this.orientation = newOrientation.reverseOrientation();
            }
        }
    }

    public void incrementAnimalsEaten() {
        this.animalsEaten = this.animalsEaten + 1;
    }

    @Override
    public Color getColor() {
        return new Color(1, 0, 0, 1);
    }
}
