package model.herbivorous;

import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Boar extends Herbivorous {

    public Boar(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.BOAR;
    }
}
