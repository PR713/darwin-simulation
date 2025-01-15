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
    //observers w przyszłości jak WorldElement może mieć różne typy obiektów a implementację MapChangeListener
    //już w swoich klasach jak Animal i Grass = WorldElement
    private final UUID id;
    protected final int initialPlantCount;
    protected final int dailyPlantGrowth;
    protected int currentPlantCount;
    protected int currentAnimalCount;
    protected int emptyPositionCount;
    protected List<Integer> theMostPopularGenome;
    protected double averageAnimalsEnergy;
    protected double averageDeathAnimalsAgeFromTheStart;
    protected double averageAliveAnimalsNumberOfChildren;
    protected Vector2d lowerLeftEquatorialForest;
    protected Vector2d upperRightEquatorialForest;
    protected GrassPlacer grassPlacer;

    public AbstractWorldMap(int height, int width, int initialPlantCount, int dailyPlantGrowth, int consumeEnergy) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width - 1, height - 1);
        this.visualizer = new MapVisualizer(this);
        this.id = UUID.randomUUID();
        this.initialPlantCount = initialPlantCount;
        this.dailyPlantGrowth = dailyPlantGrowth;
        this.lowerLeftEquatorialForest = new Vector2d(0, (int) (0.4 * height));
        this.upperRightEquatorialForest = new Vector2d(width - 1, (int) (0.6 * height));
        this.grassPlacer = new GrassPlacer(grassTufts, lowerLeft, upperRight, lowerLeftEquatorialForest, upperRightEquatorialForest, consumeEnergy);

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

    @Override
    public List<WorldElement> getAllGrassTufts() {
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

    @Override
    public abstract Boundary getCurrentBounds();


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
        Boundary bounds = getCurrentBounds();
        return visualizer.draw(bounds.bottomLeft(), bounds.topRight());
    }


    @Override
    public UUID getId() {
        return id;
    }

    public void addGrassTuft(Vector2d position, Grass grassTuft) {
        grassPlacer.addGrassTuft();
    }

}
