package agh.ics.oop.model;

import agh.ics.oop.exceptions.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected final Map<Vector2d, Grass> grassTufts = new HashMap<>();

    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected final MapVisualizer visualizer;
    private final List<MapChangeListener> observers = new ArrayList<>();
    private final UUID id;
    protected int currentPlantCount;
    protected int currentAnimalCount;
    protected int emptyPositionCount;
    protected List<Integer> theMostPopularGenome;
    protected double averageAnimalsEnergy;
    protected int deadAnimalsCount; // N*x + energy) / (N+1)
    protected double averageDeathAnimalsAgeFromTheStart;
    protected double averageAliveAnimalsNumberOfChildren;
    protected Vector2d lowerLeftEquatorialForest;
    protected Vector2d upperRightEquatorialForest;
    protected GrassPlacer grassPlacer;


    public AbstractWorldMap(int height, int width, int initialPlantCount, int dailyGrassGrowth, int consumeEnergy) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width - 1, height - 1);
        this.visualizer = new MapVisualizer(this);
        this.id = UUID.randomUUID();
        this.lowerLeftEquatorialForest = new Vector2d(0, (int) (0.4 * height));
        this.upperRightEquatorialForest = new Vector2d(width - 1, (int) (0.6 * height));
        this.grassPlacer = new GrassPlacer(grassTufts, lowerLeft, upperRight, lowerLeftEquatorialForest, upperRightEquatorialForest,
                consumeEnergy, dailyGrassGrowth, initialPlantCount);

    }


    @Override
    public void place(AbstractAnimal animal) throws IncorrectPositionException {
        if (canMoveTo(animal.getPosition())) {
            addAnimalToMap(animal);
            mapChanged(String.format("New animal placed at position: %s", animal.getPosition()));
        } else {
            throw new IncorrectPositionException(animal.getPosition());
        }

    }


    public void addAnimalToMap(AbstractAnimal animal) {
        animals.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add((Animal) animal);
    }


    public void removeAnimalFromMap(Vector2d position, AbstractAnimal animal) {
        animals.get(position).remove(animal);

        if (animals.get(position).isEmpty()) {
            animals.remove(position);
        }
    }


    @Override
    public void move(AbstractAnimal animal, MapDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        animal.move(this, direction.getNumericValue());
        Vector2d newPosition = animal.getPosition();

        if (!oldPosition.equals(newPosition)) {
            removeAnimalFromMap(oldPosition, animal);
            addAnimalToMap(animal);
            mapChanged(String.format("Animal moved from %s to %s", oldPosition, newPosition));
        }
    }


    @Override
    public boolean isOccupied(Vector2d position) {
        return isOccupiedByAnimal(position) || isOccupiedByGrass(position);
    }


    @Override
    public boolean isOccupiedByAnimal(Vector2d position) {
        return animals.containsKey(position);
    }


    @Override
    public boolean isOccupiedByGrass(Vector2d position) {
        return grassTufts.containsKey(position);
    }


    @Override
    public List<? extends WorldElement> objectAt(Vector2d position) {
        return animals.get(position);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }


    @Override
    public boolean isMovingBeyondBordersHorizontally(Vector2d position) {
        return (position.getX() > upperRight.getX() || position.getX() < lowerLeft.getX());
    }


    @Override
    public boolean isMovingBeyondBordersVertically(Vector2d position) {
        return (position.getY() > upperRight.getY() || position.getY() < lowerLeft.getY());
    }


    public Vector2d getUpperRight() {
        return upperRight;
    }


    public Vector2d getLowerLeft() {
        return lowerLeft;
    }


    @Override
    public List<Grass> getAllGrassTufts() {
        return List.copyOf(grassTufts.values());
    }


    @Override
    public List<Animal> getAllAnimals() {
        return animals.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }


    @Override
    public List<WorldElement> getAllWorldElements() {
        return Stream.concat(getAllAnimals().stream(), getAllGrassTufts().stream())
                .collect(Collectors.toList());
    }


    public void addObserver(MapChangeListener observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }


    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }


    protected void mapChanged(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }


    @Override
    public String toString() {
        return visualizer.draw(lowerLeft, upperRight);
    }


    @Override
    public UUID getId() {
        return id;
    }


    @Override
    public void addGrassTufts() {
        grassPlacer.addGrassTufts();
    }


    @Override
    public void deleteDeadAnimals() {
        for (List<Animal> animalList : animals.values()) {
            animalList.removeIf(Animal::hasPassedAway);
            this.currentAnimalCount--;
        }
    }


    @Override
    public void updateEaten() {
        for (Vector2d grassPosition : grassTufts.keySet()) {
            List<Animal> animalsOnPosition = animals.get(grassPosition);
            List<Animal> animalsOnPositionThatMoved = animalsOnPosition.stream()
                    .filter(Animal::getHasAlreadyMoved)
                    .toList();
            Animal animalWinner = solveConflictsBetweenAnimals(animalsOnPositionThatMoved);
            animalWinner.setEnergy(animalWinner.getEnergy() + grassPlacer.consumeEnergy);
        }
    }


    @Override
    public void updateReproduction() {
        Set<Vector2d> positionsWithAnimalAt = animals.keySet();
        for (Vector2d position : positionsWithAnimalAt) {
            while (true) {
                List<Animal> animalsOnPosition = animals.get(position);
                if (animalsOnPosition.size() < 2) {
                    continue;
                }

                Animal animalWinner1 = solveConflictsBetweenAnimals(animalsOnPosition);
                animalsOnPosition.remove(animalWinner1);
                Animal animalWinner2 = solveConflictsBetweenAnimals(animalsOnPosition);
                animalsOnPosition.add(animalWinner1);

                if (animalWinner1.getEnergy() < animalWinner1.getEnergyNeededToReproduce() || animalWinner2.getEnergy() < animalWinner2.getEnergyNeededToReproduce()) {
                    break;
                }

                addAnimalToMap(reproduceAnimals(animalWinner1, animalWinner2));
                animalWinner1.hasReproduced();
                animalWinner2.hasReproduced();
            }
        }
    }


    private Animal reproduceAnimals(Animal animalWinner1, Animal animalWinner2) {
        Genome newGene = new Genome(animalWinner1.getGenome(), animalWinner2.getGenome(), animalWinner1.getEnergy(), animalWinner2.getEnergy());

        MapDirection orientation = MapDirection.randomOrientation();
        int startIndexOfGenome = (int) (Math.random() * animalWinner1.getGenome().getGenes().length);
        Animal newBornedAnimal = new Animal(animalWinner1.getPosition(), orientation,
                2*animalWinner1.getEnergyNeededToReproduce(), animalWinner1.getEnergyLossPerDay(),
                animalWinner1.getEnergyLossPerReproduction(), animalWinner1.getEnergyNeededToReproduce(),
                animalWinner1.getGenome().getGenes().length, startIndexOfGenome, animalWinner1.getIsAging(), newGene);
        try {
            place(newBornedAnimal);
        } catch (IncorrectPositionException e) {
            System.out.println("Cannot place the animal: " + e.getMessage());
        }
        addAnimalToMap(newBornedAnimal);
        return newBornedAnimal;
    }


    public void updateChildrens(Animal animal) {
        animal.incrementNumberOfChildren();

    }

    @Override
    public Animal solveConflictsBetweenAnimals(List<Animal> animalsOnPosition) {
        int maxAnimalEnergy = animalsOnPosition.stream().mapToInt(Animal::getEnergy).max().orElse(-1);
        List<Animal> strongestAnimals = animalsOnPosition.stream()
                .filter(animal -> animal.getEnergy() == maxAnimalEnergy)
                .toList();

        if (strongestAnimals.size() == 1) {
            return strongestAnimals.get(0);
        }


        int maxLifespan = strongestAnimals.stream()
                .mapToInt(Animal::getNumberOfDaysAlive)
                .max()
                .orElse(-1);

        List<Animal> oldestAnimals = strongestAnimals.stream()
                .filter(animal -> animal.getNumberOfDaysAlive() == maxLifespan)
                .toList();

        if (oldestAnimals.size() == 1) {
            return oldestAnimals.get(0);
        }


        int maxNumberOfChildren = oldestAnimals.stream()
                .mapToInt(Animal::getNumberOfChildren)
                .max()
                .orElse(-1);

        List<Animal> mostProlificAnimals = oldestAnimals.stream()
                .filter(animal -> animal.getNumberOfChildren() == maxNumberOfChildren)
                .toList();

        if (mostProlificAnimals.size() == 1) {
            return mostProlificAnimals.get(0);
        }


        return mostProlificAnimals.get((int) (Math.random() * mostProlificAnimals.size()));
    }
}
