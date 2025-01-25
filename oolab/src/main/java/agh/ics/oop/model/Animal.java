package agh.ics.oop.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static agh.ics.oop.model.OldAgeMovementBehavior.isMoveSkippedDueToAge;

public class Animal extends AbstractAnimal {
    private final int initialEnergy;
    private int currentEnergy;
    private final int energyLossPerDay;
    private final int energyLossPerReproduction;
    private boolean isReadyToReproduce = false;
    private final int energyNeededToReproduce;
    private int numberOfChildren;
    private int numberOfDescendants;
    private int numberOfDaysAlive;
    private final List<Animal> children = new ArrayList<>(); //
    private List<Animal> descendants = new ArrayList<>(); //
    private boolean passedAway = false;
    private final boolean isAging;
    private boolean hasAlreadyMoved;
    private int plantsConsumed;


    public Animal(Vector2d position, MapDirection orientation,
                  int defaultEnergySpawnedWith, int energyLossPerDay, int energyLossPerReproduction,
                  int energyNeededToReproduce, int genomeLength, int startIndexOfGenome, boolean isAging,
                  Genome genome) {
        super(position, orientation, genomeLength, startIndexOfGenome, genome);
        this.currentEnergy = defaultEnergySpawnedWith;
        this.initialEnergy = defaultEnergySpawnedWith;
        this.energyLossPerDay = energyLossPerDay;
        this.energyLossPerReproduction = energyLossPerReproduction;
        this.energyNeededToReproduce = energyNeededToReproduce;
        this.isAging = isAging;
        if (defaultEnergySpawnedWith > this.energyNeededToReproduce) {
            this.isReadyToReproduce = true;
        }
        this.numberOfChildren = 0;
        this.numberOfDaysAlive = 0;
        this.numberOfDescendants = 0;
        this.plantsConsumed = 0;
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
        System.out.println("New pos: " + getPosition());
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


    public void addChild(Animal animal) {
        this.children.add(animal);
    }

    public List<Animal> getDescendants(){
        List<Animal> descendants = new ArrayList<>();
        for (Animal child : children) {
            descendants.add(child);
            descendants.addAll(child.getDescendants());
        }
        this.descendants = descendants.stream()
                .distinct()
                .toList();
        return this.descendants;
    }

    private int getNumberOfDescendants() {
        this.numberOfDescendants = getDescendants().size();
        return numberOfDescendants;
    }

    @Override
    public Color getColor() {
        return new Color(0, 0, Math.clamp(currentEnergy * 0.5f * (1f/initialEnergy), 0, 1), 1);
    }

    public boolean isNotReadyToReproduce() {
        return !isReadyToReproduce;
    }

    public void setReadyToReproduce(boolean readyToReproduce) {
        isReadyToReproduce = readyToReproduce;
    }

    public void incrementPlantsConsumed() {
        this.plantsConsumed++;
    }

    public int getPlantsConsumed() {
        return plantsConsumed;
    }
}
