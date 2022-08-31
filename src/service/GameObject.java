package service;

import model.abstraction.Animal;
import model.herb.Herb;
import model.herbivorous.Boar;
import model.herbivorous.Buffalo;
import model.herbivorous.Caterpillar;
import model.herbivorous.Deer;
import model.herbivorous.Duck;
import model.herbivorous.Goat;
import model.herbivorous.Horse;
import model.herbivorous.Mouse;
import model.herbivorous.Rabbit;
import model.herbivorous.Sheep;
import model.predator.Bear;
import model.predator.Boa;
import model.predator.Eagle;
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
            case BOA -> (T) new Boa(y, x, isYoung);
            case FOX -> (T) new Fox(y, x, isYoung);
            case BEAR -> (T) new Bear(y, x, isYoung);
            case EAGLE -> (T) new Eagle(y, x, isYoung);
            case HORSE -> (T) new Horse(y, x, isYoung);
            case DEER -> (T) new Deer(y, x, isYoung);
            case RABBIT -> (T) new Rabbit(y, x, isYoung);
            case MOUSE -> (T) new Mouse(y, x, isYoung);
            case GOAT -> (T) new Goat(y, x, isYoung);
            case SHEEP -> (T) new Sheep(y, x, isYoung);
            case BOAR -> (T) new Boar(y, x, isYoung);
            case BUFFALO -> (T) new Buffalo(y, x, isYoung);
            case DUCK -> (T) new Duck(y, x, isYoung);
            case CATERPILLAR -> (T) new Caterpillar(y, x, isYoung);
            case HERB -> (T) new Herb(y, x);
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

    public int convertPropertyToInt(String property){
        return Integer.parseInt(property);
    }
}
