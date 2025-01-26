package agh.ics.oop.presenter;


import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
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

public class SimulationPresenter implements MapChangeListener {

    public static final Color GRASS_COLOR = new Color(0, 1, 0, 1);
    public static final Color DIRT_COLOR = new Color(0.3, 0.1, 0.1, 1);
    @FXML private Label dayInfoLabel;
    @FXML private Label animalDaysAliveStat;
    @FXML private Label animalDescendantCountStat;
    @FXML private Label animalChildCountStat;
    @FXML private Label animalPlantsConsumedStat;
    @FXML private Label animalEnergyStat;
    @FXML private Label animalActiveGenomeStat;
    @FXML private Label animalGenomeStat;

    @FXML private GridPane animalDetailsGrid;

    @FXML private Label grassCountStat;
    @FXML private Label emptyCellsStat;
    @FXML private Label bestGenomeStat;
    @FXML private Label meanAnimalEnergyStat;
    @FXML private Label meanLifeLengthStat;
    @FXML private Label meanChildCountStat;
    @FXML private Label animalCountStat;

    @FXML private GridPane mapGrid;

    @FXML private Button startSimulationButton;
    @FXML private Button stopSimulationButton;

    private Animal selectedAnimal = null;

    private AbstractWorldMap worldMap;
    private Simulation simulation;
    private SimulationEngine engine;

    private static final double CELL_WIDTH = 20.0;
    private static final double CELL_HEIGHT = 20.0;

    public void setWorldMap(AbstractWorldMap map) {
        this.worldMap = map;
        drawMap();
    }

    public void setSimulation(Simulation simulation)
    {
        this.simulation = simulation;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        setWorldMap((AbstractWorldMap)worldMap);
        Platform.runLater(this::drawMap);
    }

    public void drawMap(){
        dayInfoLabel.setText(String.valueOf("Day " + simulation.getDay()));
        clearGrid();
        Vector2d lowerLeft = worldMap.getLowerLeft();
        Vector2d upperRight = worldMap.getUpperRight();

        int numberOfRows = upperRight.getY() - lowerLeft.getY() + 1;
        int numberOfColumns = upperRight.getX() - lowerLeft.getX() + 1;

        for (int i = 0; i < numberOfColumns + 1; i++) {//+1 bo jeszcze kolumna na y/x, np -1,0,1,2...
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
        for (int i = 0; i < numberOfRows + 1; i++) { //-,,-
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }


        //indeksowanie osi
        Label labelYX = new Label("y/x");
        GridPane.setHalignment(labelYX, HPos.CENTER);
        mapGrid.add(labelYX, 0, 0);

        for (int y = 1; y < numberOfRows + 1; y++){

            Label label = new Label(String.valueOf(upperRight.getY() - y + 1));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label,0, y);
        }

        for (int x = 1; x < numberOfColumns + 1; x++){
            Label label = new Label(String.valueOf(lowerLeft.getX() + x - 1));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, x, 0);
        }


        for (int y = lowerLeft.getY(); y <= upperRight.getY(); y++){
            for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++){
                Vector2d position = new Vector2d(x,y);
                WorldElement element = worldMap.objectAt(position);

                StackPane cell = getCellStackPane(worldMap.isOccupiedByGrass(position), element, position);
                Label label;
                if (element == null) {
                    label = new Label(" ");
                } else {
                    label = new Label(element.toString());
                }

                GridPane.setHalignment(cell, HPos.CENTER);
                mapGrid.add(cell, x - lowerLeft.getX() + 1, upperRight.getY() - y + 1);
                // label, column, row
            }
        }

        drawStatistics();
    }

    private void selectAnimal(Animal animal)
    {
        selectedAnimal = animal;
        stopSimulation();
        drawMap();
    }

    private void drawStatistics()
    {
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

        if (selectedAnimal != null)
        {
            animalDetailsGrid.setVisible(true);
            animalDaysAliveStat.setText(String.valueOf(selectedAnimal.getNumberOfDaysAlive()));
            animalDescendantCountStat.setText(String.valueOf(selectedAnimal.getNumberOfDescendants()));
            animalChildCountStat.setText(String.valueOf(selectedAnimal.getNumberOfChildren()));
            animalPlantsConsumedStat.setText(String.valueOf(selectedAnimal.getPlantsConsumed()));
            animalEnergyStat.setText(String.valueOf(selectedAnimal.getEnergy()));
            animalActiveGenomeStat.setText(String.valueOf("Index: " + selectedAnimal.getCurrentIndexOfGenome()));
            animalGenomeStat.setText(String.valueOf(Arrays.stream(selectedAnimal.getGenome().getGenes()).mapToObj(String::valueOf).collect(Collectors.joining())));
        }
        else
            animalDetailsGrid.setVisible(false);
    }

    StackPane getCellStackPane(boolean grass, WorldElement element, Vector2d position)
    {
        StackPane pane = new StackPane();
        pane.setMinSize(CELL_WIDTH-2, CELL_HEIGHT-2);
        pane.setMaxSize(CELL_WIDTH-2, CELL_HEIGHT-2);

        Rectangle r = new Rectangle(CELL_WIDTH-2, CELL_HEIGHT-2);

        Color cellColor = grass ? GRASS_COLOR : DIRT_COLOR;
        float weight = worldMap.getSpecialFieldWeight(position);
        cellColor = cellColor.interpolate(Color.BLACK, weight * 3);
        r.setFill(cellColor);

        pane.getChildren().add(r);

        if (element != null)
        {
            r = new Rectangle(CELL_WIDTH-9, CELL_HEIGHT-9);
            StackPane.setAlignment(r, Pos.CENTER);
            r.setFill(element.getColor());

            pane.getChildren().add(r);
            if (element instanceof Animal animal)
            {
                pane.setOnMouseClicked(e -> selectAnimal(animal));

                if (animal == selectedAnimal)
                    r.setFill(new Color(1, 1, 0, 1));
                else if (Objects.equals(worldMap.getMostPopularGenome(), animal.getGenome().toString()))
                {
                    r.setFill(new Color(element.getColor().getBlue(), 0f, element.getColor().getBlue(), 1f));
                }
            }
        }

        return pane;
    }

    @FXML
    private void startSimulation()
    {
        startSimulationButton.setDisable(true);
        stopSimulationButton.setDisable(false);
        selectedAnimal = null;

        if (engine != null)
        {
            simulation.paused = false;
            return;
        }

        engine = new SimulationEngine(List.of(simulation));
        engine.runAsync();
        new Thread(() -> {
            try {
                engine.awaitSimulationsEnd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void stopSimulation() {
        simulation.paused = true;
        startSimulationButton.setDisable(false);
        stopSimulationButton.setDisable(true);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst()); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
}