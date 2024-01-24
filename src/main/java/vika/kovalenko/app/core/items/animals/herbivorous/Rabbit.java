package vika.kovalenko.app.core.items.animals.herbivorous;

import lombok.NoArgsConstructor;
import vika.kovalenko.app.core.items.Herbivorous;

import static vika.kovalenko.app.core.constants.GameObjectName.RABBIT;

@NoArgsConstructor
public class Rabbit extends Herbivorous {
    {
        classKey = RABBIT;
        initSatietyAndWeight();
    }
}
