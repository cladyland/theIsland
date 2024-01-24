package vika.kovalenko.app.core.items.animals.predators;

import lombok.NoArgsConstructor;
import vika.kovalenko.app.core.items.Predator;

import static vika.kovalenko.app.core.constants.GameObjectName.WOLF;

@NoArgsConstructor
public class Wolf extends Predator {
    {
        classKey = WOLF;
        initSatietyAndWeight();
    }
}
