package model.herbivorous;

import model.abstraction.AbleToHunt;
import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Duck extends Herbivorous implements AbleToHunt {

    public Duck(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.DUCK;
        saturation = maxSaturation();
    }
}
