package model.herbivorous;

import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Duck extends Herbivorous {

    public Duck(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.DUCK;
    }
}
