package agh.ics.oop.model;

import static agh.ics.oop.model.MapDirection.fromNumericValue;

public class WildOwlBear extends AbstractAnimal {

    private int animalsEaten = 0;
    private final Genome genome;

    public WildOwlBear(Vector2d position, MapDirection orientation, int genomeLength, int startIndexOfGenome) {
        super(position, orientation, genomeLength, startIndexOfGenome);
        this.genome = new Genome(genomeLength);
    }


    @Override
    public void move(MoveValidator validator, int direction) {
        WildOwlBearMap map = (WildOwlBearMap) validator;
        MapDirection newOrientation = fromNumericValue((this.orientation.getNumericValue() + direction) % 8);
        Vector2d newPosition = this.position.add(this.orientation.toMapDirectionVector());

        if (map.canMoveTo(newPosition)) {
            this.position = newPosition;
            this.orientation = newOrientation;
        } else {
            if (map.isOwlBearMovingBeyondBordersHorizontally(newPosition)
                    && map.isOwlBearMovingBeyondBordersVertically(newPosition)) {
                //position doesn't change
                this.orientation = newOrientation.reverseOrientation();

            } else if (map.isOwlBearMovingBeyondBordersHorizontally(newPosition)) {
                this.position = this.position.getX() > map.getOwlBearAreaUpperRight().getX() ?
                        new Vector2d(map.getOwlBearAreaLowerLeft().getX(), this.position.getY()) :
                        new Vector2d(map.getOwlBearAreaUpperRight().getX(), this.position.getY());
                this.orientation = newOrientation.reverseOrientation();
            } else { // vertically but not in corners, position doesn't change
                this.orientation = newOrientation.reverseOrientation();
            }

            this.eatIfIsPossible(map);
        }
    }

    @Override
    public void eatIfIsPossible(AbstractWorldMap map) {
        WildOwlBearMap castedMap = (WildOwlBearMap) map;
        Vector2d position = this.getPosition();
        if (castedMap.isOccupiedByAnimal(position)) {
            castedMap.owlBearAteAnAnimal(position);
            castedMap.animals.remove(position);
            castedMap.currentPlantCount--;
            castedMap.emptyPositionCount++;
        }
    }


    public void incrementAnimalsEaten() {
        this.animalsEaten = this.animalsEaten + 1;
    }
}
