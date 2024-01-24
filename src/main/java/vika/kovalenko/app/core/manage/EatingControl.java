package vika.kovalenko.app.core.manage;

import vika.kovalenko.app.core.items.Animal;

@FunctionalInterface
public interface EatingControl {
    void findAndEatFood(Animal animal);
}
