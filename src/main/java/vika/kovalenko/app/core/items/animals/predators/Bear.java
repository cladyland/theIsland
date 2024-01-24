package vika.kovalenko.app.core.items.animals.predators;

import lombok.NoArgsConstructor;
import vika.kovalenko.app.core.items.Predator;

import static vika.kovalenko.app.core.constants.GameObjectName.BEAR;

@NoArgsConstructor
public class Bear extends Predator {
    {
        classKey = BEAR;
        initSatietyAndWeight();
    }
}
