import agh.ics.oop.OptionsParser;
import agh.ics.oop.model.MoveDirection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OptionsParserTest {
    @Test
    void testParser_WithValidInput_ReturnsCorrectDirections() {
        String[] arguments = {"f", "b", "r", "r", "l"};

        List<MoveDirection> result = OptionsParser.parser(arguments);
        List<MoveDirection> expected = List.of(
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.LEFT);

        assertEquals(expected, result);
    }

    @Test
    void testParser_WithEmptyInput() {
        String[] arguments = {};

        List<MoveDirection> result = OptionsParser.parser(arguments);
        List<MoveDirection> expected = List.of();

        assertEquals(expected, result);
    }

    @Test
    void testParser_WithIncorrectInput() {
        String[] arguments = {"f", "l", "abc", "a,", ",", "l", "b"};

        List<MoveDirection> result = OptionsParser.parser(arguments);
        List<MoveDirection> expected = List.of(
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.LEFT,
                MoveDirection.BACKWARD);

        assertEquals(expected, result);
    }
}
