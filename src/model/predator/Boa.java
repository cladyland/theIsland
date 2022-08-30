package model.predator;

import model.abstraction.Predator;
import resources.GameObjectName;

public class Boa extends Predator {

    public Boa(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        classKey = GameObjectName.BOA;
    }
}
