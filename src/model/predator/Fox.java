package model.predator;

import model.abstraction.Predator;
import resources.KeysProperties;

public class Fox extends Predator {

    public Fox(int y, int x) {
        super(y, x);
        classKey = KeysProperties.FOX;
    }

}
