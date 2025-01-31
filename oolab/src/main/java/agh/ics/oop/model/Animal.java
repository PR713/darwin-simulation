package agh.ics.oop.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class Animal extends AbstractAnimal {
    protected final int initialEnergy;
    protected int currentEnergy;
    protected final int energyLossPerDay;
    protected final int energyLossPerReproduction;
    protected boolean isReadyToReproduce = false;
    protected final int energyNeededToReproduce;
    protected int numberOfChildren;
    protected int numberOfDescendants;
    protected int numberOfDaysAlive;
    protected final List<Animal> children = new ArrayList<>(); //
    protected List<Animal> descendants = new ArrayList<>(); //
    protected boolean passedAway = false;
    protected boolean hasAlreadyMoved;
    protected int plantsConsumed;


    public Animal(Vector2d position, MapDirection orientation,
                  int defaultEnergySpawnedWith, int energyLossPerDay, int energyLossPerReproduction,
                  int energyNeededToReproduce, int startIndexOfGenome,
                  Genome genome) {
        super(position, orientation, startIndexOfGenome, genome);
        this.currentEnergy = defaultEnergySpawnedWith;
        this.initialEnergy = defaultEnergySpawnedWith;
        this.energyLossPerDay = energyLossPerDay;
        this.energyLossPerReproduction = energyLossPerReproduction;
        this.energyNeededToReproduce = energyNeededToReproduce;
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
        if (currentEnergy - energyLossPerDay >= 0) {
            setHasAlreadyMoved(true);
            super.move(validator, direction);

            setEnergy(currentEnergy - energyLossPerDay);
            isReadyToReproduce = currentEnergy >= energyNeededToReproduce;
        } else setPassedAway(true);
    }


    public boolean hasPassedAway() {
        return passedAway;
    }


    public void setPassedAway(boolean passedAway) { // czy zwierzę można wskrzesić?
        this.passedAway = passedAway;
    }


    public int getNumberOfDaysAlive() { // czemu nie getAge?
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


    public List<Animal> getDescendants() {
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


    public int getNumberOfDescendants() {
        this.numberOfDescendants = getDescendants().size();
        return numberOfDescendants;
    }


    @Override
    public Color getColor() {
        return new Color(0, 0, Math.clamp(currentEnergy * 0.5f * (1f / initialEnergy), 0, 1), 1);
    }


    public boolean isNotReadyToReproduce() {
        return !isReadyToReproduce;
    }


    public void incrementPlantsConsumed() {
        this.plantsConsumed++;
    }


    public int getPlantsConsumed() {
        return plantsConsumed;
    }
}
