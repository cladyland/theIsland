package vika.kovalenko.app.core.items.animals.herbivorous;

import lombok.NoArgsConstructor;
import vika.kovalenko.app.core.items.Animal;
import vika.kovalenko.app.core.items.Herbivorous;

import static vika.kovalenko.app.core.constants.GameObjectName.CATERPILLAR;

@NoArgsConstructor
public class Caterpillar extends Herbivorous {
    {
        classKey = CATERPILLAR;
        initSatietyAndWeight();
    }

    @Override
    protected double calculateInedibleWeight() {
        return 0;
    }

    @Override
    public <T extends Animal> T giveBirth() {
        return null;
    }
}
