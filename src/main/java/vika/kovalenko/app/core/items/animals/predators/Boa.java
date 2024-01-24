package vika.kovalenko.app.core.items.animals.predators;

import lombok.NoArgsConstructor;
import vika.kovalenko.app.core.items.Predator;

import static vika.kovalenko.app.core.constants.GameObjectName.BOA;

@NoArgsConstructor
public class Boa extends Predator {
    {
        classKey = BOA;
        initSatietyAndWeight();
    }
}
