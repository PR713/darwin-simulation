package agh.ics.oop.model;


import static agh.ics.oop.model.OldAgeMovementBehavior.isMoveSkippedDueToAge;

public class OldAgeGlobeMap extends GlobeMap {

    public OldAgeGlobeMap(int height, int width, int oldAgeBeginsOn) {
        super(height, width);
    }


    @Override
    public void move(AbstractAnimal animal, MapDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        //lub osobna klasa dla OldAgeAnimal i każda klasa metodę MovementBehavior sama implementuje
        //tu nie ma prawa być OwlBear
        if (isMoveSkippedDueToAge((Animal) animal)) {
            return;
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
