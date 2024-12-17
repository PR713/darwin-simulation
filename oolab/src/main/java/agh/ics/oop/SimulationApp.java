package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationApp extends Application {
    public void start(Stage primaryStage) throws IOException {

//        List<String> parameters = getParameters().getRaw();
//
//        GrassField grassField = new GrassField(Integer.parseInt(parameters.get(0)));
//        List<Vector2d> positions = List.of(new Vector2d(1, 1), new Vector2d(2, 4), new Vector2d(1, 3));
//
//        String[] directionsArray = new String[parameters.size() - 1];
//        for (int i = 1; i < parameters.size(); i++){
//            directionsArray[i-1] = parameters.get(i);
//        }
//
//        List<MoveDirection> directions = parseToMoveDirection(directionsArray);
//
//        ConsoleMapDisplay consoleObserver = new ConsoleMapDisplay();
//        grassField.addObserver(consoleObserver); //wypisywanie na konsolę
//
//        Simulation simulation = new Simulation(positions, directions, grassField);
//        SimulationEngine engine = new SimulationEngine(List.of(simulation));
//        engine.runAsync();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        GrassField map = new GrassField(5);
        presenter.setWorldMap(map);
        map.addObserver(presenter);
        ConsoleMapDisplay consoleDisplay = new ConsoleMapDisplay();
        map.addObserver(consoleDisplay);

        configureStage(primaryStage, viewRoot);
        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
