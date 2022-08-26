package model.predator;

import model.abstraction.Predator;
import resources.GameObjectName;
import resources.KeysProperties;

public class Fox extends Predator {

    public Fox(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.FOX;
    }

}
