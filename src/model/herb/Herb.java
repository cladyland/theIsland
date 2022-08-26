package model.herb;

import model.abstraction.Plant;
import resources.GameObjectName;

public class Herb extends Plant {

    public Herb(int y, int x) {
        super(y, x);
        classKey = GameObjectName.HERB;
    }
}
