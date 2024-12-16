package agh.ics.oop.presenter;


import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.List;

import static agh.ics.oop.OptionsParser.parseToMoveDirection;

public class SimulationPresenter implements MapChangeListener {

    private WorldMap worldMap;

    @FXML
    private Label infoLabel;

    @FXML
    private Label infoLabelMove;

    @FXML
    private TextField movementTextField;

    @FXML
    private Button startButton;

    @FXML
    private GridPane mapGrid;

    public void setWorldMap(WorldMap map) {
        this.worldMap = map;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(()-> {
            drawMap(message);
            infoLabelMove.setText(message);
        });

    }

    public void drawMap(String message){
        clearGrid();
        Vector2d lowerLeft = worldMap.getCurrentBounds().bottomLeft();
        Vector2d upperRight = worldMap.getCurrentBounds().topRight();
        

    }

    public void onSimulationStartClicked(ActionEvent actionEvent){
        List<Vector2d> positions = List.of(new Vector2d(1, 1), new Vector2d(2, 4));
        List<MoveDirection> moves = parseToMoveDirection(movementTextField.getText().split(" "));
        Simulation simulationGrassField = new Simulation(positions, moves, worldMap);
        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulationGrassField));
        simulationEngine.runAsync();
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }



}
