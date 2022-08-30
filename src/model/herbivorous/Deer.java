package model.herbivorous;

import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Deer extends Herbivorous {

    public Deer(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.DEER;
    }
}
