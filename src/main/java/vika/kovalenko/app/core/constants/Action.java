package vika.kovalenko.app.core.constants;

public enum Action {
    MOVE,
    EAT,
    REPRODUCE;

    public static int actionsCount() {
        return values().length;
    }

    public static Action defineAction(int actionNumber) {
        return values()[actionNumber];
    }
}
