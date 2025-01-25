package agh.ics.oop.presenter;


import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

import java.io.IOException;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {

    private WorldMap worldMap;
    private Simulation simulation;

    //@FXML
    //private Label infoLabel;

    @FXML
    private Label infoLabelMove;

    @FXML
    private TextField movementTextField;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Button startSimulationButton;
    @FXML
    private Button stopSimulationButton;

    private static final double CELL_WIDTH = 20.0;
    private static final double CELL_HEIGHT = 20.0;

    public void setWorldMap(WorldMap map) {
        this.worldMap = map;
        drawMap("");
    }

    public void setSimulation(Simulation simulation)
    {
        this.simulation = simulation;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        setWorldMap(worldMap);
        Platform.runLater(()-> {
            drawMap(message);
            //infoLabelMove.setText(message);
        });

    }

    public void drawMap(String message){

        clearGrid();
        Vector2d lowerLeft = worldMap.getLowerLeft();
        Vector2d upperRight = worldMap.getUpperRight();

        int numberOfRows = upperRight.getY() - lowerLeft.getY() + 1;
        int numberOfColumns = upperRight.getX() - lowerLeft.getX() + 1;

        System.out.println("drawing " + numberOfRows + " " + numberOfColumns);


        for (int i = 0; i < numberOfColumns + 1; i++) {//+1 bo jeszcze kolumna na y/x, np -1,0,1,2...
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
        for (int i = 0; i < numberOfRows + 1; i++) { //-,,-
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

        //mapGrid.add(new Rectangle(20, 20), 1, 1);

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
                List<? extends WorldElement> elements = worldMap.objectAt(position);
                WorldElement el = null;
                if (elements != null && elements.size() > 0)
                    el = elements.get(0);

                StackPane cell = getCellStackPane(worldMap.isOccupiedByGrass(position), el);
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
        StackPane newStackPane = getCellStackPane(true, null);
        GridPane.setHalignment(newStackPane, HPos.CENTER);
        GridPane.setValignment(newStackPane, VPos.CENTER);
        mapGrid.add(newStackPane, 1, 1);
        //infoLabel.setText(worldMap.toString());
    }

    StackPane getCellStackPane(boolean grass, WorldElement element)
    {
        StackPane pane = new StackPane();
        pane.setMinSize(CELL_WIDTH-2, CELL_HEIGHT-2);
        pane.setMaxSize(CELL_WIDTH-2, CELL_HEIGHT-2);

        Rectangle r = new Rectangle(CELL_WIDTH-2, CELL_HEIGHT-2);
        r.setFill(grass ? new Color(0, 1, 0, 1) : new Color(0.3, 0.1, 0.1, 1));

        pane.getChildren().add(r);

        if (element != null)
        {
            r = new Rectangle(CELL_WIDTH-9, CELL_HEIGHT-9);
            StackPane.setAlignment(r, Pos.CENTER);
            r.setFill(element.getColor());

            pane.getChildren().add(r);
            final int l = 5;
            pane.setOnMouseClicked(e -> System.out.println(l));
        }

        return pane;
    }

    @FXML
    private void startSimulation()
    {
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        engine.runAsync();
        new Thread(() -> {
            try {
                engine.awaitSimulationsEnd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void stopSimulation()
    {

    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
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
