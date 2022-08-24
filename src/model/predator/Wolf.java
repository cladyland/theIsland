package model.predator;

import model.abstraction.Predator;
import resources.KeysProperties;

public class Wolf extends Predator {

    public Wolf(int y, int x) {
        super(y, x);
        classKey = KeysProperties.WOLF;
    }

}
