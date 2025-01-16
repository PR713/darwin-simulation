package agh.ics.oop.model;

import java.util.List;
import java.util.UUID;

import static agh.ics.oop.model.OldAgeMovementBehavior.isMoveSkippedDueToAge;

public class Animal extends AbstractAnimal {
    private final int defaultEnergySpawnedWith;
    private int currentEnergy;
    private final int energyLossPerDay;
    private final int energyLossPerReproduction;
    private boolean isReadyToReproduce = false;
    private final int energyNeededToReproduce;
    private int numberOfChildren;
    private int numberOfDescendants;
    private int numberOfDaysAlive;
    private UUID parentID; //
    private List<UUID> ancestorsIDs; //
    private boolean passedAway = false;
    private boolean isAging;


    //przenieść jedzenie trawy

    public Animal(Vector2d position, MapDirection orientation,
                  int defaultEnergySpawnedWith, int energyLossPerDay, int energyLossPerReproduction,
                  int energyNeededToReproduce, int genomeLength, int startIndexOfGenome, boolean isAging) {
        super(position, orientation, genomeLength, startIndexOfGenome);
        this.defaultEnergySpawnedWith = defaultEnergySpawnedWith;
        this.energyLossPerDay = energyLossPerDay;
        this.energyLossPerReproduction = energyLossPerReproduction;
        this.energyNeededToReproduce = energyNeededToReproduce;
        this.isAging = isAging;
        if (this.defaultEnergySpawnedWith > this.energyNeededToReproduce){
            this.isReadyToReproduce = true;
        }
    }

    public int getEnergy() {
        return currentEnergy;
    }

    public void setEnergy(int newEnergy) {
        this.currentEnergy = newEnergy;
    }


    @Override
    public void move(MoveValidator validator, int direction) {
        if (currentEnergy - energyLossPerDay > 0) {
            if (isAging && isMoveSkippedDueToAge(this)) {
                return;
            }

            super.move(validator, direction);
            AbstractWorldMap map = (AbstractWorldMap) validator;
            this.eatIfIsPossible(map); // to i ^ przenieść do map
            setEnergy(currentEnergy - energyLossPerDay);
        }
        else setPassedAway(true);
    }

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

    @Override
    public void eatIfIsPossible(AbstractWorldMap map) {
        Vector2d position = this.getPosition();
        if (map.isOccupiedByGrass(position)) {
            map.grassTufts.remove(position);
            map.currentPlantCount--;
            map.emptyPositionCount++;
            this.setCurrentEnergy(this.getEnergy() + map.grassPlacer.consumeEnergy);
        }
    }

    public UUID getParentID(){
        return parentID;
    }

    public List<UUID> getAncestorsIDs(){
        return List.copyOf(ancestorsIDs);
    }
}
