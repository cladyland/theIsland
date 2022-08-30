package model.predator;

import model.abstraction.Predator;
import resources.GameObjectName;

public class Bear extends Predator {

    public Bear(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.BEAR;
    }
}
