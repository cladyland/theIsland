package model.settings;

import model.abstraction.Animal;
import model.herb.Herb;
import model.predator.Fox;
import model.predator.Wolf;
import resources.KeysProperties;

import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("unchecked")
public class GameObject {

    public <T> T createNewIslandObject(KeysProperties key, int y, int x) {
        return switch (key) {
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
    case CATERPILLAR -> new Caterpillar();*/
            case HERB -> (T) new Herb(y, x);
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

    public <T> void addToArea(ArrayList<T> area, Object obj) {
        area.add((T) obj);
    }

    public <T> void removeFromArea(ArrayList<T> area, Object obj) {
        area.remove((T) obj);
    }

    public ArrayList<? extends Animal> getAreaListByKey
            (Map<KeysProperties, ArrayList<? extends Animal>> areaMap, KeysProperties key) {
        return areaMap.get(key);
    }
}
