package agh.ics.oop.model;

import static agh.ics.oop.model.OldAgeMovementBehavior.isMoveSkippedDueToAge;

public class OldAgeWildOwlBearMap extends WildOwlBearMap {

    public OldAgeWildOwlBearMap(int height, int width, int initialPlantCount,
                                int dailyPlantGrowth, int consumeEnergy, Vector2d owlBearAreaLowerLeft,
                                Vector2d owlBearAreaUpperRight, WildOwlBear wildOwlBear) {

        super(height, width, initialPlantCount, dailyPlantGrowth, consumeEnergy, owlBearAreaLowerLeft, owlBearAreaUpperRight, wildOwlBear);
    }


    @Override
    public void move(AbstractAnimal animal, MapDirection direction) {
        //lub osobna klasa dla OldAgeAnimal i każda klasa metodę MovementBehavior sama implementuje
        animal.move(this, direction.getNumericValue());
    }
}
