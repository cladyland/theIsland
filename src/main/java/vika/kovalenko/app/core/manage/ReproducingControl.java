package vika.kovalenko.app.core.manage;

import vika.kovalenko.app.core.items.Animal;
import vika.kovalenko.app.core.manage.handler.EggsHandler;

public interface ReproducingControl {
    void reproduce(Animal animal);

    void giveBirth(Animal animal);

    void hatching(EggsHandler handler);
}
