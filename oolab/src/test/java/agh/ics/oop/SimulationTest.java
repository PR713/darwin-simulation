package agh.ics.oop;

import agh.ics.oop.model.AbstractWorldMap;
import agh.ics.oop.model.GlobeMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    @Test
    void testSimulationRun() {
        AbstractWorldMap map = new GlobeMap(15, 15, 5, 3, 6, 1, 3, false);
        Simulation simulation = new Simulation(1, map, 4, 10, 1, 5, 8, 1, false, null, false);

        simulation.run();
        assertEquals(1, map.getAllAnimals().size());

        int grassCount = map.getAllGrassTufts().size();
        assertTrue(grassCount == 8 || grassCount == 7); //zwierze moze zjesc jedna

        int animalEnergy = map.getAllAnimals().getFirst().getEnergy();
        assertTrue(animalEnergy > 8 && animalEnergy != 10);
    }
}
