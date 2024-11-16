import agh.ics.oop.model.Grass;
import agh.ics.oop.model.GrassField;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {


    @Test
    public void testIsOccupiedWithGrass() {
        GrassField grassField = new GrassField(4);
        Vector2d position = new Vector2d(2, 3);
        Grass grass = new Grass(position);
        grassField.place(grass);

        // Testujemy, czy odpowiednia pozycja jest zajęta przez trawkę
        assertEquals(grassField.isOccupied(position), "The position should be occupied by grass.");
    }
}
