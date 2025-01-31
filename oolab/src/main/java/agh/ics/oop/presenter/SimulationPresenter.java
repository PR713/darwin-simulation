package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SimulationPresenter {

    private static final Color GRASS_COLOR = new Color(0, 1, 0, 1);
    private static final Color DIRT_COLOR = new Color(0.3, 0.1, 0.1, 1);
    private static final Color SELECTED_ANIMAL_COLOR = new Color(1, 1, 0, 1);

    private static final int ANIMAL_RECTANGLE_SIZE_REDUCTION = 9;

    private static final double CELL_WIDTH = 20.0;
    private static final double CELL_HEIGHT = 20.0;

    @FXML
    private Label dayInfoLabel;
    @FXML
    private Label animalDaysAliveStat;
    @FXML
    private Label animalDescendantCountStat;
    @FXML
    private Label animalChildCountStat;
    @FXML
    private Label animalPlantsConsumedStat;
    @FXML
    private Label animalEnergyStat;
    @FXML
    private Label animalActiveGenomeStat;
    @FXML
    private Label animalGenomeStat;

    @FXML
    private GridPane animalDetailsGrid;

    @FXML
    private Label grassCountStat;
    @FXML
    private Label emptyCellsStat;
    @FXML
    private Label bestGenomeStat;
    @FXML
    private Label meanAnimalEnergyStat;
    @FXML
    private Label meanLifeLengthStat;
    @FXML
    private Label meanChildCountStat;
    @FXML
    private Label animalCountStat;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Button startSimulationButton;
    @FXML
    private Button stopSimulationButton;

    private Animal selectedAnimal = null;

    private AbstractWorldMap worldMap;
    private Simulation simulation;
    private SimulationEngine engine;

    public void setWorldMap(AbstractWorldMap map) {
        this.worldMap = map;
        drawMap();
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void drawMap() {
        clearGrid();

        Vector2d lowerLeft = worldMap.getLowerLeft();
        Vector2d upperRight = worldMap.getUpperRight();

        createEmptyGrid(lowerLeft, upperRight);
        fillGridCells(lowerLeft, upperRight);

        drawStatistics();
    }

    @FXML
    private void startSimulation() {
        startSimulationButton.setDisable(true);
        stopSimulationButton.setDisable(false);
        selectedAnimal = null;

        if (engine != null) {
            simulation.paused = false;
            return;
        }

        engine = new SimulationEngine(List.of(simulation));
        engine.runAsync();
        new Thread(() -> {
            try {
                engine.awaitSimulationsEnd(); // jaki jest sens odpalać wątek spejcalnie po to, żeby poczekał na inne wątki?
            } catch (InterruptedException e) {
                //Konczymy dzialanie
            }
        }).start();
    }

    @FXML
    private void stopSimulation() {
        simulation.paused = true;
        startSimulationButton.setDisable(false);
        stopSimulationButton.setDisable(true);
    }

    private void createEmptyGrid(Vector2d lowerLeft, Vector2d upperRight) {
        int numberOfRows = upperRight.getY() - lowerLeft.getY() + 1;
        int numberOfColumns = upperRight.getX() - lowerLeft.getX() + 1;

        for (int i = 0; i < numberOfColumns + 1; i++) { //+1 bo jeszcze kolumna na y/x, np -1,0,1,2...
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
        for (int i = 0; i < numberOfRows + 1; i++) { //-,,-
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

        //Indeksowanie osi
        Label labelYX = new Label("y/x");
        GridPane.setHalignment(labelYX, HPos.CENTER);
        mapGrid.add(labelYX, 0, 0);

        for (int y = 1; y < numberOfRows + 1; y++) {

            Label label = new Label(String.valueOf(upperRight.getY() - y + 1));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, y);
        }

        for (int x = 1; x < numberOfColumns + 1; x++) {
            Label label = new Label(String.valueOf(lowerLeft.getX() + x - 1));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, x, 0);
        }
    }

    private void fillGridCells(Vector2d lowerLeft, Vector2d upperRight) {
        for (int y = lowerLeft.getY(); y <= upperRight.getY(); y++) {
            for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
                Vector2d position = new Vector2d(x, y);
                WorldElement element = worldMap.objectAt(position);

                float specialFieldWeight = worldMap.getSpecialFieldWeight(position);
                StackPane cell = getCellStackPane(worldMap.isOccupiedByGrass(position), element, specialFieldWeight);

                GridPane.setHalignment(cell, HPos.CENTER);
                mapGrid.add(cell, x - lowerLeft.getX() + 1, upperRight.getY() - y + 1);
            }
        }
    }

    private void selectAnimal(Animal animal) {
        selectedAnimal = animal;
        stopSimulation();
        drawMap();
    }

    private void drawStatistics() {
        dayInfoLabel.setText(String.valueOf("Day " + simulation.getDay()));

        grassCountStat.setText(String.valueOf(worldMap.getAllGrassTufts().size()));
        emptyCellsStat.setText(String.valueOf(worldMap.getEmptyPositionsCount()));
        if (worldMap.getMostPopularGenome() == null)
            bestGenomeStat.setText("");
        else
            bestGenomeStat.setText(String.valueOf(worldMap.getMostPopularGenome()));
        meanAnimalEnergyStat.setText(String.valueOf(worldMap.getAverageAliveAnimalsEnergy()));
        meanChildCountStat.setText(String.valueOf(worldMap.getAverageAliveAnimalsNumberOfChildren()));
        meanLifeLengthStat.setText(String.valueOf(worldMap.getAverageDeadAnimalsAge()));
        animalCountStat.setText(String.valueOf(worldMap.getAllAnimals().size()));

        if (selectedAnimal != null) {
            animalDetailsGrid.setVisible(true);
            animalDaysAliveStat.setText(String.valueOf(selectedAnimal.getNumberOfDaysAlive()));
            animalDescendantCountStat.setText(String.valueOf(selectedAnimal.getNumberOfDescendants()));
            animalChildCountStat.setText(String.valueOf(selectedAnimal.getNumberOfChildren()));
            animalPlantsConsumedStat.setText(String.valueOf(selectedAnimal.getPlantsConsumed()));
            animalEnergyStat.setText(String.valueOf(selectedAnimal.getEnergy()));
            animalActiveGenomeStat.setText(String.valueOf("Index: " + selectedAnimal.getCurrentIndexOfGenome()));
            animalGenomeStat.setText(String.valueOf(Arrays.stream(selectedAnimal.getGenome().getGenes()).mapToObj(String::valueOf).collect(Collectors.joining())));
        } else
            animalDetailsGrid.setVisible(false);
    }

    private StackPane getCellStackPane(boolean grass, WorldElement element, float specialFieldWeight) {
        StackPane pane = new StackPane();
        //Zmniejszone o 2 ze wzgledu na border
        pane.setMinSize(CELL_WIDTH - 2, CELL_HEIGHT - 2);
        pane.setMaxSize(CELL_WIDTH - 2, CELL_HEIGHT - 2);

        Rectangle cellRectangle = new Rectangle(CELL_WIDTH - 2, CELL_HEIGHT - 2);

        Color cellColor = grass ? GRASS_COLOR : DIRT_COLOR;
        cellColor = cellColor.interpolate(Color.BLACK, specialFieldWeight * 3);
        cellRectangle.setFill(cellColor);

        pane.getChildren().add(cellRectangle);

        if (element != null) {
            Rectangle elementRectangle = createWorldElementRectangle(pane, element);
            pane.getChildren().add(elementRectangle);
        }

        return pane;
    }

    private Rectangle createWorldElementRectangle(StackPane pane, WorldElement element) {
        Rectangle rectangle = new Rectangle(CELL_WIDTH - ANIMAL_RECTANGLE_SIZE_REDUCTION, CELL_HEIGHT - ANIMAL_RECTANGLE_SIZE_REDUCTION);
        StackPane.setAlignment(rectangle, Pos.CENTER);
        rectangle.setFill(element.getColor());

        if (element instanceof Animal animal) {
            pane.setOnMouseClicked(e -> selectAnimal(animal));

            if (animal == selectedAnimal)
                rectangle.setFill(SELECTED_ANIMAL_COLOR);
            else if (Objects.equals(worldMap.getMostPopularGenome(), animal.getGenome().toString()))
                rectangle.setFill(specialColorFromNormal(element.getColor()));
        }
        return rectangle;
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst()); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private static Color specialColorFromNormal(Color normalColor) {
        return new Color(normalColor.getBlue(), 0f, normalColor.getBlue(), 1f);
    }
}