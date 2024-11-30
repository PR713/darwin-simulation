package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final List<Simulation> simulationsList;

    public SimulationEngine(List<Simulation> simulationsList) {
        this.simulationsList = simulationsList;
    };

    public void runAsync(){
        for (Simulation simulation : simulationsList){
            simulation.run();
        }
    };

    public void awaitSimulationsEnd() {

    };
}
