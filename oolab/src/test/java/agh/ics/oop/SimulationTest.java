//package agh.ics.oop;
//
//import agh.ics.oop.model.*;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class SimulationTest {
//
//    @Test
//    void withoutChangingPositionAndOrientation_EmptyInput() {
//        Vector2d position1 = new Vector2d(2,2);
//        Vector2d position2 = new Vector2d(3,4);
//        List<Vector2d> positions = List.of(position1, position2);
//        List<MoveDirection> directions = OptionsParser.parseToMoveDirection(new String[] {});
//        WorldMap map = new RectangularMap(5,5);
//        Simulation simulation = new Simulation(positions, directions, map);
//        simulation.run();
//
//        List<AbstractAnimal> animals = simulation.getAnimals();
//        List<Vector2d> expectedPositions = List.of(position1, position2);
//
//        assertTrue(animals.get(0).isAt(expectedPositions.get(0)));
//        assertTrue(animals.get(1).isAt(expectedPositions.get(1)));
//    }
//
//    @Test
//    void withChangingPositionAndOrientationNorthEast() {
//        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
//        List<MoveDirection> directions = OptionsParser.parseToMoveDirection(new String[] {"f","r","b","f"});
//        WorldMap map = new RectangularMap(5,5);
//        Simulation simulation = new Simulation(positions, directions,map);
//        simulation.run();
//
//        List<Vector2d> expectedPositions = List.of(new Vector2d(2,2), new Vector2d(4,4));
//        List<AbstractAnimal> animals = simulation.getAnimals();
//
//        assertTrue(animals.get(0).isAt(expectedPositions.get(0)));
//        assertEquals(MapDirection.NORTH, animals.get(0).getOrientation());
//
//        assertTrue(animals.get(1).isAt(expectedPositions.get(1)));
//        assertEquals(MapDirection.EAST, animals.get(1).getOrientation());
//    }
//
//    @Test
//    void withChangingPositionAndOrientationSouthWest() {
//        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,3));
//        List<MoveDirection> directions = OptionsParser.parseToMoveDirection(new String[] {"r","f","r","l"});
//        WorldMap map = new RectangularMap(5,5);
//        Simulation simulation = new Simulation(positions, directions, map);
//        simulation.run();
//
//        List<Vector2d> expectedPositions = List.of(new Vector2d(2,2), new Vector2d(3,4));
//        List<AbstractAnimal> animals = simulation.getAnimals();
//
//        assertTrue(animals.get(0).isAt(expectedPositions.get(0)));
//        assertEquals(MapDirection.SOUTH, animals.get(0).getOrientation());
//
//        assertTrue(animals.get(1).isAt(expectedPositions.get(1)));
//        assertEquals(MapDirection.WEST, animals.get(1).getOrientation());
//    }
//
//    @Test
//    void movingOutsideBoundariesOfMapNorthEastEdge() {
//        Vector2d position1 = new Vector2d(4,1);
//        Vector2d position2 = new Vector2d(2,4);
//        List<Vector2d> positions = List.of(position1, position2);
//        List<MoveDirection> directions = OptionsParser.parseToMoveDirection(new String [] {"r","f","f"});
//        WorldMap map = new RectangularMap(5,5);
//        Simulation simulation = new Simulation(positions, directions, map);
//        simulation.run();
//
//        List<Vector2d> expectedPositions = List.of(position1, position2);
//        List<AbstractAnimal> animals = simulation.getAnimals();
//
//        assertTrue(animals.get(0).isAt(expectedPositions.get(0)));
//        assertEquals(MapDirection.EAST, animals.get(0).getOrientation());
//
//        assertTrue(animals.get(1).isAt(expectedPositions.get(1)));
//        assertEquals(MapDirection.NORTH, animals.get(1).getOrientation());
//    }
//
//    @Test
//    void movingOutsideBoundariesOfMapSouthWestEdge() {
//        Vector2d position1 = new Vector2d(0,1);
//        Vector2d position2 = new Vector2d(2,0);
//        List<Vector2d> positions = List.of(position1, position2);
//        List<MoveDirection> directions = OptionsParser.parseToMoveDirection(new String [] {"l","b","f"});
//        WorldMap map = new RectangularMap(5,5);
//        Simulation simulation = new Simulation(positions, directions, map);
//        simulation.run();
//
//        List<Vector2d> expectedPositions = List.of(position1, position2);
//        List<AbstractAnimal> animals = simulation.getAnimals();
//
//        assertTrue(animals.get(0).isAt(expectedPositions.get(0)));
//        assertEquals(MapDirection.WEST, animals.get(0).getOrientation());
//
//        assertTrue(animals.get(1).isAt(expectedPositions.get(1)));
//        assertEquals(MapDirection.NORTH, animals.get(1).getOrientation());
//    }
//}
