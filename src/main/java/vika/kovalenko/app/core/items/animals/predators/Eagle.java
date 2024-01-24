package vika.kovalenko.app.core.items.animals.predators;

import lombok.NoArgsConstructor;
import vika.kovalenko.app.core.items.Predator;

import static vika.kovalenko.app.core.constants.GameObjectName.EAGLE;

@NoArgsConstructor
public class Eagle extends Predator {
    {
        classKey = EAGLE;
        initSatietyAndWeight();
    }
}
