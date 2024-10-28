import agh.ics.oop.OptionsParser;
import agh.ics.oop.model.MoveDirection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.*;

public class OptionsParserTest {
    @Test
    void testParser_WithValidInput_ReturnsCorrectDirections() {
        String[] arguments = {"f", "b", "r", "r", "l"};

        MoveDirection[] result = OptionsParser.parser(arguments);
        MoveDirection[] expected = {
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.LEFT};

        assertArrayEquals(expected, result);
    }

    @Test
    void testParser_WithEmptyInput() {
        String[] arguments = {};

        MoveDirection[] result = OptionsParser.parser(arguments);
        MoveDirection[] expected = {};

        assertArrayEquals(expected, result);
    }
}
