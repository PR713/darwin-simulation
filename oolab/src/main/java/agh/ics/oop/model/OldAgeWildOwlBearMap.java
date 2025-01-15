package agh.ics.oop.model;

import static agh.ics.oop.model.OldAgeMovementBehavior.isMoveSkippedDueToAge;

public class OldAgeWildOwlBearMap extends WildOwlBearMap {

    public OldAgeWildOwlBearMap( int height, int width, Vector2d owlBearAreaLowerLeft,
              Vector2d owlBearAreaUpperRight, WildOwlBear wildOwlBear) {
        super(height, width, owlBearAreaLowerLeft, owlBearAreaUpperRight, wildOwlBear);
    }


    @Override
    public void move(AbstractAnimal animal, MapDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        //lub osobna klasa dla OldAgeAnimal i każda klasa metodę MovementBehavior sama implementuje
        if ( animal instanceof Animal) {
            if (isMoveSkippedDueToAge( animal)) {
                return;
            }
        }

        animal.move(this, direction.getNumericValue());
        Vector2d newPosition = animal.getPosition();

        if (!oldPosition.equals(newPosition)) {
            removeAnimalFromMap(oldPosition, animal);
            addAnimalToMap(animal);
            mapChanged(String.format("Animal moved from %s to %s", oldPosition, newPosition));
        }
    }
}
