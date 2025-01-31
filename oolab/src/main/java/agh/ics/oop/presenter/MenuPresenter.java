package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.exceptions.WrongInputException;
import agh.ics.oop.model.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MenuPresenter {
    @FXML
    private TextField energyUsageField;
    @FXML
    private TextField simulationDurationField;
    @FXML
    private TextField xSizeField;
    @FXML
    private TextField ySizeField;
    @FXML
    private TextField animalCountField;
    @FXML
    private TextField grassCountField;
    @FXML
    private TextField grassEnergyField;
    @FXML
    private TextField minMutationCountField;
    @FXML
    private TextField genomeLengthField;
    @FXML
    private TextField grassGrowthField;
    @FXML
    private TextField baseAnimalEnergyField;
    @FXML
    private TextField energyToReproduceField;
    @FXML
    private TextField reproductionEnergyConsumptionField;
    @FXML
    private TextField maxMutationCountField;
    @FXML
    private CheckBox agingCheckbox;
    @FXML
    private CheckBox owlBearCheckbox;
    @FXML
    private CheckBox saveLogCheckbox;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private VBox configList;
    @FXML
    private TextField configNameField;

    private List<Config> configs;

    @FXML
    public void initialize() {
        configs = new ArrayList<>();
        configs.addAll(List.of(Config.loadConfigs()));
        renderConfigList();
    }

    @FXML
    private void onSimulationStartClicked(ActionEvent actionEvent) {
        errorMessageLabel.setText("");
        Config config;
        try {
            config = readFieldsToConfig();
        } catch (WrongInputException exception) {
            errorMessageLabel.setText(exception.getMessage());
            return;
        }

        openNewSimulationWindow(config);
    }

    @FXML
    private void createNewConfig() {
        errorMessageLabel.setText("");
        Config newConfig;
        try {
            newConfig = readFieldsToConfig();
        } catch (WrongInputException exception) {
            errorMessageLabel.setText(exception.getMessage());
            return;
        }
        configs.add(newConfig);
        Config.saveConfigs(configs.toArray(new Config[0]));
        renderConfigList();
    }

    private void openNewSimulationWindow(Config config) {
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

            AbstractWorldMap map = getMapVariant(config);
            Simulation simulation = new Simulation(config.initialPopulation(), map, config.genomeLength(), config.initialAnimalEnergy(), config.dailyEnergyUsage(), config.reproductionConsumedEnergy(), config.reproductionMinEnergy(), 1000, config.aging(), presenter, saveLogCheckbox.isSelected());
            presenter.setSimulation(simulation);
            presenter.setWorldMap(map);

            simulationStage.setOnCloseRequest(e -> simulation.disposeSimulation());
        } catch (IOException exception) {
            errorMessageLabel.setText("Something went wrong when creating simulation.");
        }
    }

    private static AbstractWorldMap getMapVariant(Config config) {
        AbstractWorldMap map;
        if (config.wildOwlBear())
            map = new WildOwlBearMap(config.sizeY(),
                    config.sizeX(),
                    config.initialGrassCount(),
                    config.dailyGrassGrowth(),
                    config.grassEnergy(),
                    config.minMutations(),
                    config.maxMutations(),
                    config.genomeLength(), // dlaczego ta mapa dostaje długość genomu, a GlobeMap nie?
                    config.aging());
        else
            map = new GlobeMap(config.sizeY(),
                    config.sizeX(),
                    config.initialGrassCount(),
                    config.dailyGrassGrowth(),
                    config.grassEnergy(),
                    config.minMutations(),
                    config.maxMutations(),
                    config.aging());
        return map;
    }

    private void renderConfigList() {
        configList.getChildren().clear();
        for (Config config : configs) {
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

    private void applyConfig(Config config) {
        errorMessageLabel.setText("");
        //Ustawiamy wszystkie pola tekstowe
        xSizeField.setText(String.valueOf(config.sizeX()));
        ySizeField.setText(String.valueOf(config.sizeY()));
        animalCountField.setText(String.valueOf(config.initialPopulation()));
        grassCountField.setText(String.valueOf(config.initialGrassCount()));
        grassEnergyField.setText(String.valueOf(config.grassEnergy()));
        grassGrowthField.setText(String.valueOf(config.dailyGrassGrowth()));
        baseAnimalEnergyField.setText(String.valueOf(config.initialAnimalEnergy()));
        energyToReproduceField.setText(String.valueOf(config.reproductionMinEnergy()));
        reproductionEnergyConsumptionField.setText(String.valueOf(config.reproductionConsumedEnergy()));
        minMutationCountField.setText(String.valueOf(config.minMutations()));
        maxMutationCountField.setText(String.valueOf(config.maxMutations()));
        genomeLengthField.setText(String.valueOf(config.genomeLength()));
        energyUsageField.setText(String.valueOf(config.dailyEnergyUsage()));
        simulationDurationField.setText(String.valueOf(config.simulationDuration()));
        agingCheckbox.setSelected(config.aging());
        owlBearCheckbox.setSelected(config.wildOwlBear());
    }

    private Config readFieldsToConfig() throws WrongInputException {
        String name = configNameField.getText();

        boolean aging = agingCheckbox.isSelected();
        boolean owlBear = owlBearCheckbox.isSelected();
        int sizeX = getValidatedFieldValue(xSizeField, 5, 25, "Map X Size");
        int sizeY = getValidatedFieldValue(ySizeField, 5, 25, "Map Y Size");
        int animalCount = getValidatedFieldValue(animalCountField, 0, sizeX * sizeY, "Start Animal Count");
        int grassCount = getValidatedFieldValue(grassCountField, 0, sizeX * sizeY, "Start Grass Count");
        int grassEnergy = getValidatedFieldValue(grassEnergyField, 0, Integer.MAX_VALUE, "Grass Energy");
        int grassGrowth = getValidatedFieldValue(grassGrowthField, 0, sizeX * sizeY, "Grass Growth");
        int baseAnimalEnergy = getValidatedFieldValue(baseAnimalEnergyField, 1, Integer.MAX_VALUE, "Base Animal Energy");
        int energyToReproduce = getValidatedFieldValue(energyToReproduceField, 0, Integer.MAX_VALUE, "Minimum Energy To Reproduce");
        int reproductionConsumption = getValidatedFieldValue(reproductionEnergyConsumptionField, 0, Integer.MAX_VALUE, "Reproduction Energy Consumption");
        int minMutationCount = getValidatedFieldValue(minMutationCountField, 0, Integer.MAX_VALUE, "Minimum Mutation Count");
        int maxMutationCount = getValidatedFieldValue(maxMutationCountField, minMutationCount, Integer.MAX_VALUE, "Maximum Mutation Count");
        int genomeLength = getValidatedFieldValue(genomeLengthField, 1, Integer.MAX_VALUE, "Genome Length");
        int dailyEnergyUsage = getValidatedFieldValue(energyUsageField, 0, Integer.MAX_VALUE, "Daily Energy Usage");
        int simulationDuration = getValidatedFieldValue(simulationDurationField, 1, Integer.MAX_VALUE, "Simulation Duration");

        return new Config(name, sizeX, sizeY, animalCount, grassCount, grassEnergy, grassGrowth, baseAnimalEnergy, energyToReproduce, reproductionConsumption, minMutationCount, maxMutationCount, genomeLength, dailyEnergyUsage, simulationDuration, aging, owlBear);
    }

    private int getValidatedFieldValue(TextField field, int minVal, int maxVal, String fieldName) throws WrongInputException {
        int value;
        try {
            value = parseInt(field.getText());
        } catch (Exception exception) {
            throw new WrongInputException(fieldName + " field has invalid value.");
        }

        if (value < minVal)
            throw new WrongInputException(fieldName + " field value cannot be smaller than " + minVal + ".");
        if (value > maxVal)
            throw new WrongInputException(fieldName + " field value cannot be bigger than " + maxVal + ".");

        return value;
    }

    private void removeConfig(Config config) {
        configs.remove(config);
        Config.saveConfigs(configs.toArray(new Config[0]));
        renderConfigList();
    }
}
