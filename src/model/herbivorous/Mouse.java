package model.herbivorous;

import model.abstraction.AbleToHunt;
import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Mouse extends Herbivorous implements AbleToHunt {

    public Mouse(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.MOUSE;
        saturation = maxSaturation();
    }
}
