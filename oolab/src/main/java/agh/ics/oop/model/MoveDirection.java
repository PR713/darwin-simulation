//package agh.ics.oop.model;
//
//public enum MoveDirection {
//    FORWARD(0),
//    FORWARD_RIGHT(1),
//    RIGHT(2),
//    BACKWARD_RIGHT(3),
//    BACKWARD(4),
//    BACKWARD_LEFT(5),
//    LEFT(6),
//    FORWARD_LEFT(7);
//
//    private final int numericValue;
//
//    MoveDirection(int numericValue) {
//        this.numericValue = numericValue;
//    }
//
//    public int getNumericValue() {
//        return numericValue;
//    }
//
//    public static MoveDirection fromNumericValue(int numericValue) {
//        return switch (numericValue) {
//            case 0 -> FORWARD;
//            case 1 -> FORWARD_RIGHT;
//            case 2 -> RIGHT;
//            case 3 -> BACKWARD_RIGHT;
//            case 4 -> BACKWARD;
//            case 5 -> BACKWARD_LEFT;
//            case 6 -> LEFT;
//            case 7 -> FORWARD_LEFT;
//            default -> throw new IllegalArgumentException("Invalid numeric value: " + numericValue);
//        };
//    }
//
//}
