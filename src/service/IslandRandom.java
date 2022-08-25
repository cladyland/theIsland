package service;

import model.settings.GameObject;
import resources.KeysProperties;

import java.util.ArrayList;
import java.util.Random;

import static resources.KeysProperties.COUNT;
import static resources.KeysProperties.HERB;
import static resources.KeysProperties.MAX;
import static resources.KeysProperties.SPEED;

public class IslandRandom extends Random {
    private final GameObject gameObject;

    public IslandRandom() {
        gameObject = new GameObject();
    }

    public <T> ArrayList<T> createInitialObjects(KeysProperties key, ArrayList<T> area, int y, int x) {
        int initialNumberOfAnimals = randomNumberOfNewObjects(key);
        while (initialNumberOfAnimals != 0) {
            area.add(gameObject.createNewIslandObject(key, y, x));
            initialNumberOfAnimals--;
        }
        return area;
    }

    public KeysProperties directionMovement() {
        IslandRandom islandRandom = new IslandRandom();
        return switch (islandRandom.nextInt(4)) {
            case 0 -> KeysProperties.UP;
            case 1 -> KeysProperties.DOWN;
            case 2 -> KeysProperties.LEFT;
            case 3 -> KeysProperties.RIGHT;
            default -> null;
        };
    }

    public int animalSpeed(KeysProperties animalName) {
        int maxSpeed = getIntPropertyKey(animalName, MAX, SPEED);
        int speed = this.nextInt(maxSpeed);
        return speed + 1;
    }

    private int randomNumberOfNewObjects(KeysProperties key) {
        if (key.equals(HERB)){
            return getIntPropertyKey(HERB, MAX, COUNT);
        }
        int maxCountOfAnimal = getIntPropertyKey(key, MAX, COUNT);
        return this.nextInt(maxCountOfAnimal / 2);
    }

    private int getIntPropertyKey(KeysProperties... keys){
        return Integer.parseInt(FindAppProperties.getAppProperty(keys));
    }
}
