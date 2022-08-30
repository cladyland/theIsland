package model.herbivorous;

import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Sheep extends Herbivorous {

    public Sheep(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.SHEEP;
    }
}
