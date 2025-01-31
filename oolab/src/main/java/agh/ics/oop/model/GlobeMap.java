package agh.ics.oop.model;

public class GlobeMap extends AbstractWorldMap { // po co jest AbstractWorldMap, jeśli GlobeMap mogłoby być klasą bazową?

    public GlobeMap(int height, int width, int initialPlantCount, int dailyPlantGrowth, int consumeEnergy, int minNumberOfMutations, int maxNumberOfMutations, boolean isAging) {
        super(height, width, initialPlantCount, dailyPlantGrowth, consumeEnergy, maxNumberOfMutations, minNumberOfMutations, isAging);
    }
}
