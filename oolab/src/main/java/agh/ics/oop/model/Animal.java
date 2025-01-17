package agh.ics.oop.model;

import java.util.List;
import java.util.UUID;

import static agh.ics.oop.model.OldAgeMovementBehavior.isMoveSkippedDueToAge;

public class Animal extends AbstractAnimal {
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
    private final boolean isAging;
    private boolean hasAlreadyMoved;


    public Animal(Vector2d position, MapDirection orientation,
                  int defaultEnergySpawnedWith, int energyLossPerDay, int energyLossPerReproduction,
                  int energyNeededToReproduce, int genomeLength, int startIndexOfGenome, boolean isAging, Genome genome) {
        super(position, orientation, genomeLength, startIndexOfGenome, genome);
        this.energyLossPerDay = energyLossPerDay;
        this.energyLossPerReproduction = energyLossPerReproduction;
        this.energyNeededToReproduce = energyNeededToReproduce;
        this.isAging = isAging;
        if (defaultEnergySpawnedWith > this.energyNeededToReproduce) {
            this.isReadyToReproduce = true;
        }
        this.numberOfDaysAlive = 0;
    }

    public int getEnergy() {
        return currentEnergy;
    }

    public void setEnergy(int newEnergy) {
        this.currentEnergy = newEnergy;
    }


    @Override
    public void move(MoveValidator validator, int direction) {
        setHasAlreadyMoved(false);
        if (currentEnergy - energyLossPerDay > 0) {
            if (isAging && isMoveSkippedDueToAge(this)) {
                return;
            }
            setHasAlreadyMoved(true);
            super.move(validator, direction);

            setEnergy(currentEnergy - energyLossPerDay);
            isReadyToReproduce = currentEnergy >= energyNeededToReproduce;
        } else setPassedAway(true);
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

    public UUID getParentID() {
        return parentID;
    }

    public List<UUID> getAncestorsIDs() {
        return List.copyOf(ancestorsIDs);
    }

    public void setHasAlreadyMoved(boolean hasAlreadyMoved) {
        this.hasAlreadyMoved = hasAlreadyMoved;
    }

    public boolean getHasAlreadyMoved() {
        return this.hasAlreadyMoved;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public int getEnergyNeededToReproduce() {
        return energyNeededToReproduce;
    }

    public int getEnergyLossPerDay() {
        return energyLossPerDay;
    }

    public int getEnergyLossPerReproduction() {
        return energyLossPerReproduction;
    }

    public boolean getIsAging() {
        return isAging;
    }

    public void incrementNumberOfChildren() {
        this.numberOfChildren++;
    }

    public void hasReproduced() {
        this.isReadyToReproduce = false;
        currentEnergy -= energyNeededToReproduce;
    }

    public void incrementNumberOfDaysAlive() {
        this.numberOfDaysAlive++;
    }
}
