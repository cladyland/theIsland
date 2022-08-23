package model.settings;

import model.abstraction.Animal;
import resources.KeysProperties;

import java.util.ArrayList;
import java.util.Map;

public class GameObject {

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
