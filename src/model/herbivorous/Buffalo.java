package model.herbivorous;

import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Buffalo extends Herbivorous {

    public Buffalo(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.BUFFALO;
    }
}
