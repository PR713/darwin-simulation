package agh.ics.oop.model;

public class OwlBear extends AbstractAnimal{


    public OwlBear(Vector2d vector, MapDirection orientation) {
        super(vector, orientation);
    }

    @Override
    public void move(MoveValidator validator, int direction) {
        // może wywołuje super ale z opcjonalnym argumentem przekazywanym
        //do canMoveTo który określa granicę
    }
}
