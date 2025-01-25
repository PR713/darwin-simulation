package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MenuPresenter
{
    @FXML
    private TextField xSizeField;

    @FXML
    private TextField ySizeField;

    @FXML
    private VBox configList;

    @FXML
    private TextField configNameField;

    private List<Config> configs;

    @FXML
    private Label errorMessageLabel;

    @FXML
    public void initialize()
    {
        configs = new ArrayList<>();
        configs.addAll(List.of(Config.loadConfigs()));
        renderConfigList();
    }

    @FXML
    private void onSimulationStartClicked(ActionEvent actionEvent)
    {
        errorMessageLabel.setText("");
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

    private void renderConfigList()
    {
        configList.getChildren().clear();
        for (Config config : configs)
        {
            HBox newElement = new HBox();
            Button configButton = new Button(config.name());
            Button deleteButton = new Button("X");

            configButton.setOnAction(e -> applyConfig(config));
            deleteButton.setOnAction(e -> removeConfig(config));

            configButton.setPrefWidth(100);
            newElement.getChildren().addAll(configButton, deleteButton);
            configList.getChildren().add(newElement);
        }
    }

    private void applyConfig(Config config)
    {
        System.out.println("aplikuje konfig " + config.name());
    }

    @FXML
    private void createNewConfig()
    {
        configs.add(new Config(configNameField.getText(), 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, false, false));
        Config.saveConfigs(configs.toArray(new Config[0]));
        renderConfigList();

    }

    private void removeConfig(Config config)
    {
        configs.remove(config);
        Config.saveConfigs(configs.toArray(new Config[0]));
        renderConfigList();

    }
}
