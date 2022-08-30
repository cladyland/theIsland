package model.herbivorous;

import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Horse extends Herbivorous {

    public Horse(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.HORSE;
    }
}
