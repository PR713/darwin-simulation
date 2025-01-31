package agh.ics.oop.model;

public enum MapDirection {
    N(new Vector2d(0, 1), 0),
    NE(new Vector2d(1, 1), 1),
    E(new Vector2d(1, 0), 2),
    SE(new Vector2d(1, -1), 3),
    S(new Vector2d(0, -1), 4),
    SW(new Vector2d(-1, -1), 5),
    W(new Vector2d(-1, 0), 6),
    NW(new Vector2d(-1, 1), 7);

    private final Vector2d mapDirectionVector;
    private final int numericValue;

    MapDirection(Vector2d mapDirectionVector, int numericValue) {
        this.mapDirectionVector = mapDirectionVector;
        this.numericValue = numericValue;
    }


    public Vector2d toMapDirectionVector() {
        return this.mapDirectionVector;
    }

    public int getNumericValue() {
        return ordinal();
    }

    public static MapDirection fromNumericValue(int numericValue) {
        return values()[numericValue];
    }


    public MapDirection reverseOrientation() {
        return fromNumericValue((this.getNumericValue() + 4) % 8);
    }

    public static MapDirection randomOrientation() {
        return fromNumericValue((int) (Math.random() * 8));
    }

    @Override
    public String toString() {
        return switch (this) {
            case N -> "Północ";
            case S -> "Południe";
            case W -> "Zachód";
            case E -> "Wschód";
            case NE -> "Północny wschód";
            case SE -> "Południowy wschód";
            case SW -> "Południowy zachód";
            case NW -> "Północny zachód";
        };
    }
}
