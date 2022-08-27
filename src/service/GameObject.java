package service;

import model.abstraction.Animal;
import model.herb.Herb;
import model.predator.Fox;
import model.predator.Wolf;
import resources.GameObjectName;
import resources.KeysProperties;

import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("unchecked")
public class GameObject {
    public <T> T createNewIslandObject(GameObjectName objectName, int y, int x, boolean isYoung) {
        return switch (objectName) {
            case WOLF -> (T) new Wolf(y, x, isYoung);
            //   case BOA -> new Boa();
            case FOX -> (T) new Fox(y, x, isYoung);
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
            default -> throw new IllegalStateException("Unexpected value: " + objectName);
        };
    }

    public <T> void addToArea(ArrayList<T> area, Object obj) {
        area.add((T) obj);
    }

    public <T> void removeFromArea(ArrayList<T> area, Object obj) {
        area.remove((T) obj);
    }

    public ArrayList<? extends Animal> getAreaListByKey
            (Map<KeysProperties, ArrayList<? extends Animal>> areaMap, GameObjectName key) {
        return areaMap.get(key);
    }
    public ArrayList<? extends Animal> convertListToAnimalList(ArrayList<?> areaMap) {
        return (ArrayList<? extends Animal>) areaMap;
    }
}
