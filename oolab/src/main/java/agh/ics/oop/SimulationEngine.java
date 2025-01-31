package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final List<Simulation> simulationsList;
    private final List<Thread> threads = new ArrayList<>();

    public SimulationEngine(List<Simulation> simulationsList) {
        this.simulationsList = simulationsList;
    }

    ; // co to?

    public void runAsync() {
        for (Simulation simulation : simulationsList) {
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
    }

    ; // jw.

    public void awaitSimulationsEnd() throws InterruptedException {
        for (Thread thread : threads) { //belongs to runAsync()
            thread.join();
        }
    }
}
