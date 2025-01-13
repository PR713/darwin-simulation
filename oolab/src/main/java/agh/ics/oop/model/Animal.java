package agh.ics.oop.model;

public class Animal extends AbstractAnimal {
    private final int defaultEnergySpawnedWith;
    private int energyProvidedByPlant;
    private int currentEnergy;
    private final int energyLossPerDay;
    private final int energyLossPerReproduction;
    private final boolean isReadyToReproduce = false;
    private int numberOfChildren;
    private int numberOfDescendants;
    private int numberOfDaysAlive;
    private boolean passedAway = false;


    public Animal(Vector2d position, MapDirection orientation, int plantEnergy,
                  int defaultEnergySpawnedWith, int energyLossPerDay, int energyLossPerReproduction) {
        super(position, orientation);
        this.defaultEnergySpawnedWith = defaultEnergySpawnedWith;
        this.energyProvidedByPlant = plantEnergy;
        this.energyLossPerDay = energyLossPerDay;
        this.energyLossPerReproduction = energyLossPerReproduction;
    }

    public int getEnergy() {
        return currentEnergy;
    }

    public void setEnergy(int newEnergy) {
        this.currentEnergy = newEnergy;
        //kwestia zjedzenia bądź nie rośliny
    }


    @Override
    public void move(MoveValidator validator, int direction) {
        if (currentEnergy - energyLossPerDay > 0) {
            super.move(validator, direction);
            AbstractWorldMap map = (AbstractWorldMap) validator;
            map.eatGrassIfPossible(this);
            currentEnergy -= energyLossPerDay;
        }
        else setPassedAway(true);
    }


//    @Override
//    public String toString() {
//        return "Animal{" +
//                "position=" + getPosition() +
//                ", orientation=" + getOrientation() +
//                ", energy=" + energy +
//                ", name='" + name + '\'' +
//                '}';
//    }


    public boolean hasPassedAway() {
        return passedAway;
    }

    public void setPassedAway(boolean passedAway) {
        this.passedAway = passedAway;
    }

    public int getNumberOfDaysAlive() {
        return numberOfDaysAlive;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }
}
