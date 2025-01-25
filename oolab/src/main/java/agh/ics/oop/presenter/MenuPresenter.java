package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.exceptions.WrongInputException;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;

public class MenuPresenter
{
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
        Config config;
        try
        {
            config = readFieldsToConfig();
        }
        catch (WrongInputException exception)
        {
            errorMessageLabel.setText(exception.getMessage());
            return;
        }


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

            AbstractWorldMap map = new GlobeMap(config.sizeY(), config.sizeX(), config.initialGrassCount(), config.dailyGrassGrowth(), config.grassEnergy());
            Simulation simulation = new Simulation(List.of(new Vector2d(4,1)), map, config.genomeLength(), config.initialAnimalEnergy(), 1, config.reproductionConsumedEnergy(), config.reproductionMinEnergy(), 100, config.aging(), presenter);
            presenter.setWorldMap(map);
            presenter.setSimulation(simulation);
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
        agingCheckbox.setSelected(config.aging());
        owlBearCheckbox.setSelected(config.wildOwlBear());
    }

    @FXML
    private void createNewConfig()
    {
        errorMessageLabel.setText("");
        Config newConfig;
        try
        {
            newConfig = readFieldsToConfig();
        }
        catch (WrongInputException exception)
        {
            errorMessageLabel.setText(exception.getMessage());
            return;
        }
        configs.add(newConfig);
        Config.saveConfigs(configs.toArray(new Config[0]));
        renderConfigList();
    }

    private Config readFieldsToConfig() throws WrongInputException
    {
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

        return new Config(name, sizeX, sizeY, animalCount, grassCount, grassEnergy, grassGrowth, baseAnimalEnergy, energyToReproduce, reproductionConsumption, minMutationCount, maxMutationCount, genomeLength, aging, owlBear);
    }

    private int getValidatedFieldValue(TextField field, int minVal, int maxVal, String fieldName) throws WrongInputException
    {
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

    private void removeConfig(Config config)
    {
        configs.remove(config);
        Config.saveConfigs(configs.toArray(new Config[0]));
        renderConfigList();
    }
}
