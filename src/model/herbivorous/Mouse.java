package model.herbivorous;

import model.abstraction.Herbivorous;
import resources.GameObjectName;

public class Mouse extends Herbivorous {

    public Mouse(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.MOUSE;
    }
}
