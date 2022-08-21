package service;

import model.predator.Fox;
import model.predator.Wolf;
import resources.KeysProperties;
import java.util.ArrayList;
import java.util.Random;
import static resources.KeysProperties.MAX;
import static resources.KeysProperties.SPEED;

public class IslandRandom extends Random {

    public <T> ArrayList<T> createInitialObjects(KeysProperties key, ArrayList<T> area, int y, int x) {
        T t = switch (key) {
            case WOLF -> (T) new Wolf(y, x);
            //   case BOA -> new Boa();
            case FOX -> (T) new Fox(y, x);
/*            case BEAR -> new Bear();
    case EAGLE -> new Eagle();
    case HORSE -> new Horse();
    case DEER -> new Deer();
    case RABBIT -> new Rabbit();
    case MOUSE -> new Mouse();
    case GOAT -> new Goat();
    case SHEEP -> new Sheep();
    case BOAR -> new Boar();
    case BUFFALO -> new Buffalo();
    case DUCK -> new Duck();
    case CATERPILLAR -> new Caterpillar();
    case HERB -> new Herb();*/
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
        area.add(0, t);
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

    public int animalSpeed(String animalName){
        KeysProperties name = KeysProperties.valueOf(animalName.toUpperCase());
        IslandRandom islandRandom = new IslandRandom();
        int maxSpeed = Integer.parseInt(FindAppProperties.getAppProperty(name, MAX, SPEED));
        int speed = islandRandom.nextInt(maxSpeed);
        return speed + 1;
    }
}
