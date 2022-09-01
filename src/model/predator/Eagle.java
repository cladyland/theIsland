package model.predator;

import model.abstraction.Predator;
import resources.GameObjectName;

public class Eagle extends Predator {

    public Eagle(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.EAGLE;
        saturation = maxSaturation();
    }
}
