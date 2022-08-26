package service;

import model.settings.GameObject;
import resources.GameObjectName;
import resources.MoveDirection;
import resources.KeysProperties;

import java.util.ArrayList;
import java.util.Random;

import static resources.GameObjectName.HERB;
import static resources.KeysProperties.COUNT;
import static resources.KeysProperties.MAX;
import static resources.KeysProperties.SPEED;

public class IslandRandom {
    private final GameObject GAME_OBJECT;
    private final Random RANDOM;

    public IslandRandom() {
        GAME_OBJECT = new GameObject();
        RANDOM = new Random();
    }

    public <T> ArrayList<T> createInitialObjects(GameObjectName key, ArrayList<T> area, int y, int x) {
        int initialNumberOfAnimals = randomNumberOfNewObjects(key);
        while (initialNumberOfAnimals != 0) {
            area.add(GAME_OBJECT.createNewIslandObject(key, y, x));
            initialNumberOfAnimals--;
        }
        return area;
    }

    public MoveDirection directionMovement() {
        return switch (RANDOM.nextInt(4)) {
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

    private int randomNumberOfNewObjects(GameObjectName key) {
        if (key.equals(HERB)){
            return getIntPropertyKey(HERB, MAX, COUNT);
        }
        int maxCountOfAnimal = getIntPropertyKey(key, MAX, COUNT);
        return RANDOM.nextInt(maxCountOfAnimal / 2);
    }

    private int getIntPropertyKey(GameObjectName name, KeysProperties... keys){
        return Integer.parseInt(FindAppProperties.getInstance().getAppProperty(name, keys));
    }
}
