package vika.kovalenko.app.core.items.animals.herbivorous;

import lombok.NoArgsConstructor;
import vika.kovalenko.app.core.items.Herbivorous;
import vika.kovalenko.app.core.items.functional.AbleToHunt;

import static vika.kovalenko.app.core.constants.GameObjectName.DUCK;

@NoArgsConstructor
public class Duck extends Herbivorous implements AbleToHunt {
    {
        classKey = DUCK;
        initSatietyAndWeight();
    }
}
