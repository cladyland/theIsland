package model.herbivorous;

import model.abstraction.AbleToHunt;
import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Boar extends Herbivorous implements AbleToHunt {

    public Boar(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.BOAR;
    }
}
