package agh.ics.oop.model;

import java.util.Map;

public enum MapDirection {
    N(new Vector2d(0, 1), 0),
    S(new Vector2d(0, -1), 1),
    W(new Vector2d(-1, 0), 2),
    E(new Vector2d(1, 0), 3),
    NE(new Vector2d(1, 1), 4),
    SE(new Vector2d(1, -1), 5),
    SW(new Vector2d(-1, -1), 6),
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
        return numericValue;
    }

    public static MapDirection fromNumericValue(int numericValue){
        return switch (numericValue) {
            case 0 -> MapDirection.N;
            case 1 -> MapDirection.S;
            case 2 -> MapDirection.W;
            case 3 -> MapDirection.E;
            case 4 -> MapDirection.NE;
            case 5 -> MapDirection.SE;
            case 6 -> MapDirection.SW;
            case 7 -> MapDirection.NW;
            default -> throw new IllegalArgumentException("Invalid numeric value: " + numericValue);
        };
    }


    public MapDirection reverseOrientation(){
        return fromNumericValue((this.getNumericValue() + 4) % 8);
    }

    public static MapDirection randomOrientation(){
        return fromNumericValue((int) (Math.random() * 8));
    }

    @Override
    public String toString(){
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
