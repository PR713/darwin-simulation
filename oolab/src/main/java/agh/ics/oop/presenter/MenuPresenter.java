package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.AbstractWorldMap;
import agh.ics.oop.model.GlobeMap;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MenuPresenter
{
    @FXML
    private TextField xSizeField;

    @FXML
    private TextField ySizeField;

    @FXML
    private void onSimulationStartClicked(ActionEvent actionEvent){
        /*
        List<Vector2d> positions = List.of(new Vector2d(1, 1), new Vector2d(2, 4));
        AbstractWorldMap map = new GlobeMap(5, 5, 5, 2, 1);


        Simulation simulationGrassField = new Simulation(positions, map, 6, 6, 1, 3, 4, 100, true);

        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulationGrassField));

        simulationEngine.runAsync();
        new Thread(() -> {
            try {
                simulationEngine.awaitSimulationsEnd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();*/
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            Stage simulationStage = new Stage();
            simulationStage.setTitle("Simulation");

            BorderPane borderPane = loader.load();
            Scene scene = new Scene(borderPane);
            simulationStage.setScene(scene);
            SimulationPresenter presenter = loader.getController();
            simulationStage.show();

            WorldMap map = new GlobeMap(parseInt(xSizeField.getText()), parseInt(ySizeField.getText()), 5, 3, 6);
            presenter.setWorldMap(map);

        }
        catch (IOException exception)
        {

        }

    }
}
