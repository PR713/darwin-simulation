package agh.ics.oop;

import agh.ics.oop.model.MapChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationEngine {
    private final List<Simulation> simulationsList;
    private final List<Thread> threads = new ArrayList<>();

    public SimulationEngine(List<Simulation> simulationsList) {
        this.simulationsList = simulationsList;
    };

    public void runSync(){ //synchronicznie -> sekwencyjnie
        for (Simulation simulation : simulationsList){
            simulation.run();
        }
    };

    public void runAsync() {
        for (Simulation simulation : simulationsList) {
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
    };

    public void awaitSimulationsEnd(){
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
