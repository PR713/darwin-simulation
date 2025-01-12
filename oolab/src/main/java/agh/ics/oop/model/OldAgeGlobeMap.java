package agh.ics.oop.model;

import agh.ics.oop.model.Animal;

public class OldAgeGlobeMap extends GlobeMap {

    private final int oldAgeBeginsOn;

    public OldAgeGlobeMap(int height, int width, int oldAgeBeginsOn) {
        super(height, width);
        this.oldAgeBeginsOn = oldAgeBeginsOn;
    }


    @Override
    public void move(AbstractAnimal animal, MapDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        Animal animal1 = (Animal) animal;
        if (animal1.getNumberOfDaysAlive() >= oldAgeBeginsOn) {
            // jakaś logika z klasy OldAgeGenes co pomija ruch
            if (true){
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

    public int getOldAgeBeginsOn() {
        return oldAgeBeginsOn;
    }

}
