package vika.kovalenko.app.core.items.animals.herbivorous;

import lombok.NoArgsConstructor;
import vika.kovalenko.app.core.items.Herbivorous;

import static vika.kovalenko.app.core.constants.GameObjectName.HORSE;

@NoArgsConstructor
public class Horse extends Herbivorous {
    {
        classKey = HORSE;
        initSatietyAndWeight();
    }
}
