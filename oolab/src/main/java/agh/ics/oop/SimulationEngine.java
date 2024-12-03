package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulationsList;
    private final List<Thread> threads = new ArrayList<>();
    private ExecutorService executorService;

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

    public void awaitSimulationsEnd() throws InterruptedException{
            executorService.shutdown();
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                System.err.println("Pula wątków nie zakończyła działania w ciągu 10 sekund");
                executorService.shutdownNow();
            }

//            for (Thread thread : threads) { //belongs to runAsync()
//                try {
//                    thread.join();
//                } catch (InterruptedException e) { //lub lepiej throws do maina
//                    System.err.println("Wątek został przerwany: " + thread.getName());
//                    Thread.currentThread().interrupt();
//                }
//            }
    }

    public void runAsyncInThreadPool(){
        executorService = Executors.newFixedThreadPool(4);
        for (Simulation simulation : simulationsList){
            executorService.submit(simulation);
        }
    }
}
