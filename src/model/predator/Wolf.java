package model.predator;

import model.abstraction.Predator;
import resources.GameObjectName;

public class Wolf extends Predator {

    public Wolf(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.WOLF;
    }

}
