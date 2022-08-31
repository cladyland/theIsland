package model.herbivorous;

import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Rabbit extends Herbivorous {

    public Rabbit(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.RABBIT;
    }
}
