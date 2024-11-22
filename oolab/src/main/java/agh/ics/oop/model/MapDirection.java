package agh.ics.oop.model;

public enum MapDirection {
    NORTH(new Vector2d(0, 1)),
    SOUTH(new Vector2d(0, -1)),
    WEST(new Vector2d(-1, 0)),
    EAST(new Vector2d(1, 0));

    private final Vector2d unitVector;

    MapDirection(Vector2d unitVector) {
        this.unitVector = unitVector;
    }


    @Override
    public String toString(){
        return switch (this) {
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
            case EAST -> "Wschód";
        };
    }


    public MapDirection next(){
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }


    public MapDirection previous() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
    }


    public Vector2d toUnitVector() {
        return this.unitVector;
    }
}
