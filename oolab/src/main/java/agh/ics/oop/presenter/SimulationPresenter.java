package agh.ics.oop.presenter;


import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {

    private WorldMap worldMap;

    //@FXML
    //private Label infoLabel;

    @FXML
    private Label infoLabelMove;

    @FXML
    private TextField movementTextField;

    @FXML
    private GridPane mapGrid;

    private static final double CELL_WIDTH = 50.0;
    private static final double CELL_HEIGHT = 50.0;


    public void setWorldMap(WorldMap map) {
        this.worldMap = map;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        setWorldMap(worldMap);
        Platform.runLater(()-> {
            drawMap(message);
            infoLabelMove.setText(message);
        });

    }

    public void drawMap(String message){
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
                List<? extends WorldElement> element = worldMap.objectAt(position);
                Label label;
                if (element == null) {
                    label = new Label(" ");
                } else {
                    label = new Label(element.toString());
                }

                GridPane.setHalignment(label, HPos.CENTER);
                mapGrid.add(label, x - lowerLeft.getX() + 1, upperRight.getY() - y + 1);
                // label, column, row
            }
        }

        //infoLabel.setText(worldMap.toString());
    }


    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }


    @FXML
    private void onSimulationStartClicked(ActionEvent actionEvent){
        List<Vector2d> positions = List.of(new Vector2d(1, 1), new Vector2d(2, 4));
        AbstractWorldMap map = new GlobeMap(5, 5, 5, 2, 1);
        map.addObserver(this);

        Simulation simulationGrassField = new Simulation(positions, map, 6, 6, 1, 3, 4, 100, true);

        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulationGrassField));

        simulationEngine.runAsync();
        new Thread(() -> {
            try {
                simulationEngine.awaitSimulationsEnd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        //bez awaitSimulationsEnd() bo wtedy join() blokuje główny wątek GUI,
        //lub jak wyżej uruchamiamy to w nowym wątku i już nie blokuje wątku GUI
    }


    @FXML
    private void newGame() {
        SimulationApp simulationApp = new SimulationApp();
        try {
            simulationApp.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
