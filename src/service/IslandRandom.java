package service;

import resources.GameObjectName;
import resources.MoveDirection;
import resources.KeysProperties;

import java.util.ArrayList;
import java.util.Random;

import static resources.GameObjectName.HERB;
import static resources.KeysProperties.COUNT;
import static resources.KeysProperties.MAX;
import static resources.KeysProperties.SPEED;
import static resources.KeysProperties.YOUNG;

public class IslandRandom {
    private final GameObject GAME_OBJECT;
    private final Random RANDOM;

    public IslandRandom() {
        GAME_OBJECT = new GameObject();
        RANDOM = new Random();
    }

    public <T> ArrayList<T> createInitialObjects(GameObjectName key, ArrayList<T> area, int y, int x) {
        int initialNumberOfAnimals = randomNumberOfInitialObjects(key);
        while (initialNumberOfAnimals != 0) {
            area.add(GAME_OBJECT.createNewIslandObject(key, y, x, false));
            initialNumberOfAnimals--;
        }
        return area;
    }

    public MoveDirection directionMovement() {
        int numberOfDirections = 4;
        return switch (RANDOM.nextInt(numberOfDirections)) {
            case 0 -> MoveDirection.UP;
            case 1 -> MoveDirection.DOWN;
            case 2 -> MoveDirection.LEFT;
            case 3 -> MoveDirection.RIGHT;
            default -> null;
        };
    }

    public int animalSpeed(GameObjectName animalName) {
        int maxSpeed = getIntPropertyKey(animalName, MAX, SPEED);
        int speed = RANDOM.nextInt(maxSpeed);
        return speed + 1;
    }

    public int selectObjectToEat(int menuSize) {
        if (menuSize <= 0) {
            return menuSize;
        }
        return RANDOM.nextInt(menuSize);
    }

    public boolean toMate() {
        return RANDOM.nextBoolean();
    }

    public int probableNumberOfYoung(GameObjectName animalName) {
        int maxYoung = getIntPropertyKey(animalName, MAX, YOUNG);
        return RANDOM.nextInt(maxYoung);
    }

    private int randomNumberOfInitialObjects(GameObjectName key) {
        if (key.equals(HERB)) {
            return getIntPropertyKey(HERB, MAX, COUNT);
        }
        int halfOfMaxAnimalCount = getIntPropertyKey(key, MAX, COUNT) / 2;
        return RANDOM.nextInt(halfOfMaxAnimalCount);
    }

    private int getIntPropertyKey(GameObjectName name, KeysProperties... keys) {
        return Integer.parseInt(AppProperties.getInstance().getAppProperty(name, keys));
    }
}
