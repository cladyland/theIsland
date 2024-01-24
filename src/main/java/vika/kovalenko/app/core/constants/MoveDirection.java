package vika.kovalenko.app.core.constants;

public enum MoveDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static int directionsCount() {
        return values().length;
    }

    public static MoveDirection defineDirection(int directionNumber) {
        return values()[directionNumber];
    }
}
