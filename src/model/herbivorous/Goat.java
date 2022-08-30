package model.herbivorous;

import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Goat extends Herbivorous {

    public Goat(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.GOAT;
    }
}
