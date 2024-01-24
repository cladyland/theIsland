package vika.kovalenko.app.core.items.animals.herbivorous;

import lombok.NoArgsConstructor;
import vika.kovalenko.app.core.items.Herbivorous;

import static vika.kovalenko.app.core.constants.GameObjectName.SHEEP;

@NoArgsConstructor
public class Sheep extends Herbivorous {
    {
        classKey = SHEEP;
        initSatietyAndWeight();
    }
}
