package agh.ics.oop.model;

public class GlobeMap extends AbstractWorldMap {

    public GlobeMap(int height, int width, int initialPlantCount, int dailyPlantGrowth, int consumeEnergy) {
        super(height, width, initialPlantCount, dailyPlantGrowth, consumeEnergy, 5, 2);
    }

}
