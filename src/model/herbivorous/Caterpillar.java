package model.herbivorous;

import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Caterpillar extends Herbivorous {

    public Caterpillar(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.CATERPILLAR;
    }
}
