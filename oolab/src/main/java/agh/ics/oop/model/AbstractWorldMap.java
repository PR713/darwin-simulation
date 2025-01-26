package agh.ics.oop.model;

import agh.ics.oop.SimulationStatistics;
import agh.ics.oop.exceptions.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected final Map<Vector2d, Grass> grassTufts = new HashMap<>();
    protected final Map<String, Integer> allGenomes = new HashMap<>();

    private final boolean isAging;

    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected final MapVisualizer visualizer;
    private final List<MapChangeListener> observers = new ArrayList<>();
    private final UUID id;
    protected int currentPlantCount;
    protected int currentAnimalsCount;
    protected int emptyPositionsCount;
    protected int countOfDeadAnimals;
    protected final int maxNumberOfMutations;
    protected final int minNumberOfMutations;
    protected String theMostPopularGenome;
    protected double averageAliveAnimalsEnergy;
    protected double averageDeadAnimalsAge;
    protected double averageAliveAnimalsNumberOfChildren = 0;
    protected Vector2d lowerLeftEquatorialForest;
    protected Vector2d upperRightEquatorialForest;
    protected GrassPlacer grassPlacer;
    protected SimulationStatistics simulationStatistics = new SimulationStatistics();
    //TO DO^^^^^


    public AbstractWorldMap(int height, int width, int initialPlantCount, int dailyGrassGrowth,
                            int consumeEnergy, int maxNumberOfMutations, int minNumberOfMutations,
                            boolean isAging) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width - 1, height - 1);
        this.averageDeadAnimalsAge = 0;
        this.visualizer = new MapVisualizer(this);
        this.maxNumberOfMutations = maxNumberOfMutations;
        this.minNumberOfMutations = minNumberOfMutations;
        this.id = UUID.randomUUID();
        this.lowerLeftEquatorialForest = new Vector2d(0, (int) (0.4 * height));
        this.upperRightEquatorialForest = new Vector2d(width - 1, (int) (0.6 * height));
        this.grassPlacer = new GrassPlacer(grassTufts, lowerLeft, upperRight, lowerLeftEquatorialForest, upperRightEquatorialForest,
                consumeEnergy, dailyGrassGrowth, initialPlantCount);
        this.isAging = isAging;
    }


    @Override
    public void place(AbstractAnimal animal) throws IncorrectPositionException {
        if (canMoveTo(animal.getPosition())) {
            addAnimalToMap(animal);
            currentAnimalsCount++;
            allGenomes.compute(animal.getGenome().toString(), (key, value) -> value == null ? 1 : value + 1);
            mapChanged(String.format("New animal placed at position: %s", animal.getPosition()));
        } else {
            throw new IncorrectPositionException(animal.getPosition());
        }

    }


    public void addAnimalToMap(AbstractAnimal animal) {

        if (isAging) {
            animals.computeIfAbsent(animal.getPosition(), k -> new LinkedList<>()).add((OldAgeAnimal) animal);
        } else {
            animals.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>()).add((Animal) animal);
        }
    }


    protected void removeAnimalFromMap(Vector2d position, AbstractAnimal animal) {
        if (animal == null)
            return;

        List<Animal> animalsAtPosition = animals.get(position);

        if (animalsAtPosition != null) {
            animalsAtPosition.remove((Animal) animal);
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
    public WorldElement objectAt(Vector2d position) {
        if (animals.get(position) == null)
            return null;
        Animal objectAtAnimal = animals.get(position).stream()
                .max(Comparator.comparingInt(Animal::getEnergy))
                .orElse(null);
        return objectAtAnimal != null ? objectAtAnimal :
                (grassTufts.get(position) != null ? grassTufts.get(position) : null);
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
        int previousCountOfDeadAnimals = this.countOfDeadAnimals;
        int totalDeadAgeToday = 0;
        List<Animal> animalsToRemove = new LinkedList<>();
        for (List<Animal> animalList : animals.values()) {
            for (Animal animal : animalList) {
                if (!animal.hasPassedAway())
                    continue;

                animalsToRemove.add(animal);
                System.out.println("Animal pos: " + animal.getPosition());
                this.currentAnimalsCount--;
                this.countOfDeadAnimals++;
                totalDeadAgeToday += animal.getNumberOfDaysAlive();
            }
        }
        for (Animal deadAnimal : animalsToRemove)
            removeAnimalFromMap(deadAnimal.getPosition(), deadAnimal);

        if (countOfDeadAnimals > 0)
        {
            averageDeadAnimalsAge = (double) (averageDeadAnimalsAge * previousCountOfDeadAnimals
                    + totalDeadAgeToday) / countOfDeadAnimals;
        }
    }


    @Override
    public void updateEaten() {
        List<Vector2d> tuftsToRemovePositions = new LinkedList<>();
        for (Vector2d grassPosition : grassTufts.keySet()) {
            List<Animal> animalsOnPosition = animals.get(grassPosition);
            if (animalsOnPosition == null)
                continue;
            List<Animal> animalsOnPositionThatMoved = animalsOnPosition.stream()
                    .filter(Animal::getHasAlreadyMoved)
                    .toList();
            if (animalsOnPositionThatMoved.isEmpty()){
                continue;
            }
            Animal animalWinner = solveConflictsBetweenAnimals(animalsOnPositionThatMoved);
            currentPlantCount -= 1;
            animalWinner.setEnergy(animalWinner.getEnergy() + grassPlacer.consumeEnergy);
            animalWinner.incrementPlantsConsumed();
            tuftsToRemovePositions.add(grassPosition);
        }

        for (Vector2d positionToRemove : tuftsToRemovePositions)
            grassTufts.remove(positionToRemove);

        this.emptyPositionsCount = grassPlacer.findEmptySpots(lowerLeft, upperRight).size();
    }


    @Override
    public void updateReproduction() {
        Set<Vector2d> positionsWithAnimalAt = animals.keySet();
        for (Vector2d position : positionsWithAnimalAt) {

            List<Animal> animalsOnPosition = animals.get(position);
            if (animalsOnPosition.size() < 2) {
                continue;
            }

            Animal animalWinner1 = solveConflictsBetweenAnimals(animalsOnPosition);
            animalsOnPosition.remove(animalWinner1);
            Animal animalWinner2 = solveConflictsBetweenAnimals(animalsOnPosition);
            animalsOnPosition.add(animalWinner1);

            if (animalWinner1.isNotReadyToReproduce() || animalWinner2.isNotReadyToReproduce()) {
                continue;
            }
            
            reproduceAnimals(animalWinner1, animalWinner2);
            animalWinner1.hasReproduced();
            animalWinner2.hasReproduced();
        }
    }


    private void reproduceAnimals(Animal animalWinner1, Animal animalWinner2) {
        Genome newGene = new Genome(animalWinner1.getGenome(), animalWinner2.getGenome(),
                animalWinner1.getEnergy(), animalWinner2.getEnergy(),
                minNumberOfMutations, maxNumberOfMutations);

        MapDirection orientation = MapDirection.randomOrientation();
        int startIndexOfGenome = (int) (Math.random() * animalWinner1.getGenome().getGenes().length);
        Animal newBornedAnimal;
        if(isAging){
            newBornedAnimal = new OldAgeAnimal(animalWinner1.getPosition(), orientation,
                    2 * animalWinner1.getEnergyNeededToReproduce(), animalWinner1.getEnergyLossPerDay(),
                    animalWinner1.getEnergyLossPerReproduction(), animalWinner1.getEnergyNeededToReproduce(),
                    animalWinner1.getGenome().getGenes().length, startIndexOfGenome, newGene);
        }else{
            newBornedAnimal = new Animal(animalWinner1.getPosition(), orientation,
                    2 * animalWinner1.getEnergyNeededToReproduce(), animalWinner1.getEnergyLossPerDay(),
                    animalWinner1.getEnergyLossPerReproduction(), animalWinner1.getEnergyNeededToReproduce(),
                    animalWinner1.getGenome().getGenes().length, startIndexOfGenome, newGene);
        }

        try {
            place(newBornedAnimal);
        } catch (IncorrectPositionException e) {
            System.out.println("Cannot place the animal: " + e.getMessage());
        }
        animalWinner1.addChild(newBornedAnimal);
        animalWinner2.addChild(newBornedAnimal);
        updateCountOfChildren(animalWinner1, animalWinner2);
        updateAverageAliveAnimalsNumberOfChildren();
    }


    public void updateCountOfChildren(Animal animal1, Animal animal2) {
        animal1.incrementNumberOfChildren();
        animal2.incrementNumberOfChildren();

    }

    @Override
    public void moveAnimals()
    {
        List<Animal> animalsToMove = new LinkedList<>();
        for (List<Animal> a : animals.values())
        {
            for (Animal animal : a) {
                animalsToMove.add(animal);
            }
        }

        for (Animal anim : animalsToMove)
        {
            anim.setHasAlreadyMoved(false);
            int direction = anim.getGenome().getGenes()[anim.getCurrentIndexOfGenome()];
            move(anim, MapDirection.fromNumericValue(direction));
            anim.incrementIndex();
        }

    }

    @Override
    public Animal solveConflictsBetweenAnimals(List<Animal> animalsOnPosition) {
        int maxAnimalEnergy = animalsOnPosition.stream()
                .mapToInt(Animal::getEnergy)
                .max()
                .orElse(-1);
        List<Animal> strongestAnimals = animalsOnPosition.stream()
                .filter(animal -> animal.getEnergy() == maxAnimalEnergy)
                .toList();

        if (strongestAnimals.size() == 1) {
            return strongestAnimals.getFirst();
        }


        int maxLifespan = strongestAnimals.stream()
                .mapToInt(Animal::getNumberOfDaysAlive)
                .max()
                .orElse(-1);

        List<Animal> oldestAnimals = strongestAnimals.stream()
                .filter(animal -> animal.getNumberOfDaysAlive() == maxLifespan)
                .toList();

        if (oldestAnimals.size() == 1) {
            return oldestAnimals.getFirst();
        }


        int maxNumberOfChildren = oldestAnimals.stream()
                .mapToInt(Animal::getNumberOfChildren)
                .max()
                .orElse(-1);

        List<Animal> mostProlificAnimals = oldestAnimals.stream()
                .filter(animal -> animal.getNumberOfChildren() == maxNumberOfChildren)
                .toList();

        if (mostProlificAnimals.size() == 1) {
            return mostProlificAnimals.getFirst();
        }

        int randomValue = (int)(Math.random() * mostProlificAnimals.size());
        return mostProlificAnimals.get(randomValue);
    }


    public void updateAverageAliveAnimalsNumberOfChildren() {
        int currentNumberOfChildren = 0;
        for (List<Animal> animalList : animals.values()) {
            for (Animal animal : animalList) {
                if (animal.hasPassedAway()) {
                    continue;
                }
                currentNumberOfChildren += animal.getNumberOfChildren();
            }
        }
        if (currentAnimalsCount > 0) {
            averageAliveAnimalsNumberOfChildren = (double) currentNumberOfChildren / currentAnimalsCount;
        }
    }


    public void updateAverageAliveAnimalsEnergy() {
        int currentAnimalsEnergy = 0;
        int todayDied = 0;
        for (List<Animal> animalList : animals.values()) {
            for (Animal animal : animalList) {
                if (animal.hasPassedAway()) {
                    todayDied++;
                    continue;
                }
                currentAnimalsEnergy += animal.getEnergy();
            }
        }
        if (currentAnimalsCount - todayDied > 0) {
            //System.out.println("Energy sum: " + currentAnimalsEnergy + "    Animals: " + currentAnimalsCount);
            averageAliveAnimalsEnergy = (double) currentAnimalsEnergy / (currentAnimalsCount-todayDied);
        }
        System.out.println("Animals count: " + currentAnimalsCount
                + "    Dead animals count: " + todayDied
                + "    Average energy: " + averageAliveAnimalsEnergy
        + "    Energy sum: " + currentAnimalsEnergy);
    }

    public void updateAnimalsLifespan() {
        for (List<Animal> animalsOnPosition : animals.values()) {
            for (Animal animal : animalsOnPosition) {
                if (!animal.hasPassedAway()) {
                    animal.incrementNumberOfDaysAlive();
                }
            }
        }
    }

    public void updateMostPopularGenome() {
        List<String> sortedGenomes = allGenomes.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(1)
                .map(Map.Entry::getKey)
                .toList();

        if (sortedGenomes.isEmpty())
        {
            theMostPopularGenome = "";
        }

        this.theMostPopularGenome = sortedGenomes.getFirst();
    }

    public String getMostPopularGenome() {
        return theMostPopularGenome;
    }


    public double getAverageAliveAnimalsEnergy() {
        updateAverageAliveAnimalsEnergy();
        return averageAliveAnimalsEnergy;
    }

    public double getAverageDeadAnimalsAge() {
        return averageDeadAnimalsAge;
    }

    public double getAverageAliveAnimalsNumberOfChildren() {
        return averageAliveAnimalsNumberOfChildren;
    }

    public int getEmptyPositionsCount() {
        return emptyPositionsCount;
    }

    public float getSpecialFieldWeigth(Vector2d position) {
        //Indicates that this field is special
        return position.precedes(upperRightEquatorialForest) && position.follows(lowerLeftEquatorialForest) ? 0.1f : 0f;
    }
}
