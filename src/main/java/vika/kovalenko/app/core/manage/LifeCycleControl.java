package vika.kovalenko.app.core.manage;

import vika.kovalenko.app.core.items.Animal;
import vika.kovalenko.app.core.items.Plant;
import vika.kovalenko.app.core.manage.handler.EggsHandler;
import vika.kovalenko.app.core.manage.handler.SerializableHandler;

public interface LifeCycleControl extends SerializableHandler {
    void addAnimalToQueue(Animal animal);

    void addCaterpillarEggsToQueue(EggsHandler handler);

    void addPlantToQueue(Plant plant);

    void processLifeCycle();

    void removeRemains();
}
