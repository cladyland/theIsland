package vika.kovalenko.app.core.manage;

import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.items.Animal;

public interface AnimalManager {
    void chooseAndPerformAction(Animal animal);

    boolean isCaterpillar(GameObjectName objectName);
}
