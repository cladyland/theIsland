package vika.kovalenko.app.core.utility.util;

import vika.kovalenko.app.core.constants.Action;
import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.constants.MoveDirection;

import java.util.Random;
import java.util.Set;

public final class IslandRandom {
    private static final Random RANDOM = new Random();

    private IslandRandom() {
    }

    public static int getRandomNumber(int number) {
        if (number == 0) return 0;
        return RANDOM.nextInt(number);
    }

    public static Action chooseAnimalAction() {
        int actionNumber = getRandomNumber(Action.actionsCount());
        return Action.defineAction(actionNumber);
    }

    public static MoveDirection chooseDirectionMovement() {
        int directionNumber = getRandomNumber(MoveDirection.directionsCount());
        return MoveDirection.defineDirection(directionNumber);
    }

    public static GameObjectName chooseFoodType(Set<GameObjectName> foodNames) {
        int index = getRandomNumber(foodNames.size());
        return foodNames.stream().toList().get(index);
    }

    public static boolean actionSuccessful() {
        return RANDOM.nextBoolean();
    }
}
