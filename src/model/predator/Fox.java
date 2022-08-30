package model.predator;

import model.abstraction.Predator;
import resources.GameObjectName;

public class Fox extends Predator {

    public Fox(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.FOX;
    }

}
