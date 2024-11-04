import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    @Test
    void withoutChangingPositionAndOrientation_EmptyInput() {
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        List<MoveDirection> directions = OptionsParser.parser(new String[] {});
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();

        List<Animal> animals = simulation.getAnimals();
        List<Vector2d> expectedPositions = List.of(new Vector2d(2,2), new Vector2d(3,4));

        assertTrue(animals.get(0).isAt(expectedPositions.get(0)));
        assertTrue(animals.get(1).isAt(expectedPositions.get(1)));
    }

    @Test
    void withChangingPositionAndOrientationNorthEast() {
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        List<MoveDirection> directions = OptionsParser.parser(new String[] {"f","r","b","f"});
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();

        List<Vector2d> expectedPositions = List.of(new Vector2d(2,2), new Vector2d(4,4));
        List<Animal> animals = simulation.getAnimals();

        assertTrue(animals.get(0).isAt(expectedPositions.get(0)));
        assertEquals(MapDirection.NORTH, animals.get(0).orientation);

        assertTrue(animals.get(1).isAt(expectedPositions.get(1)));
        assertEquals(MapDirection.EAST, animals.get(1).orientation);
    }

    @Test
    void withChangingPositionAndOrientationSouthWest() {
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,3));
        List<MoveDirection> directions = OptionsParser.parser(new String[] {"r","f","r","l"});
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();

        List<Vector2d> expectedPositions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        List<Animal> animals = simulation.getAnimals();

        assertTrue(animals.get(0).isAt(expectedPositions.get(0)));
        assertEquals(MapDirection.SOUTH, animals.get(0).orientation);

        assertTrue(animals.get(1).isAt(expectedPositions.get(1)));
        assertEquals(MapDirection.WEST, animals.get(1).orientation);
    }


    @Test
    void movingOutsideBoundariesOfMapNorthEastEdge() {
        List<Vector2d> positions = List.of(new Vector2d(4,1), new Vector2d(2,4));
        List<MoveDirection> directions = OptionsParser.parser(new String [] {"r","f","f"});
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();

        List<Vector2d> expectedPositions = List.of(new Vector2d(4,1), new Vector2d(2,4));
        List<Animal> animals = simulation.getAnimals();

        assertTrue(animals.get(0).isAt(expectedPositions.get(0)));
        assertEquals(MapDirection.EAST, animals.get(0).orientation);

        assertTrue(animals.get(1).isAt(expectedPositions.get(1)));
        assertEquals(MapDirection.NORTH, animals.get(1).orientation);
    }


    @Test
    void movingOutsideBoundariesOfMapSouthWestEdge() {
        List<Vector2d> positions = List.of(new Vector2d(0,1), new Vector2d(2,0));
        List<MoveDirection> directions = OptionsParser.parser(new String [] {"l","b","f"});
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();

        List<Vector2d> expectedPositions = List.of(new Vector2d(0,1), new Vector2d(2,0));
        List<Animal> animals = simulation.getAnimals();

        assertTrue(animals.get(0).isAt(expectedPositions.get(0)));
        assertEquals(MapDirection.WEST, animals.get(0).orientation);

        assertTrue(animals.get(1).isAt(expectedPositions.get(1)));
        assertEquals(MapDirection.NORTH, animals.get(1).orientation);
    }
}
