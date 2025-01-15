package agh.ics.oop.model;


import static agh.ics.oop.model.OldAgeMovementBehavior.isMoveSkippedDueToAge;

public class OldAgeGlobeMap extends GlobeMap {

    public OldAgeGlobeMap(int height, int width, int initialPlantCount, int dailyPlantGrowth, int consumeEnergy) {
        super(height, width, initialPlantCount, dailyPlantGrowth, consumeEnergy);
    }


    @Override
    public void move(AbstractAnimal animal, MapDirection direction) {
        //lub osobna klasa dla OldAgeAnimal i każda klasa metodę MovementBehavior sama implementuje
        //tu nie ma prawa być OwlBear
        if (isMoveSkippedDueToAge((Animal) animal)) {
            return;
        }//else
        super.move(animal, direction);
    }
}
