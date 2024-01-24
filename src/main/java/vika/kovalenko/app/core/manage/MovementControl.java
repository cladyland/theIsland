package vika.kovalenko.app.core.manage;

import vika.kovalenko.app.core.items.Animal;

@FunctionalInterface
public interface MovementControl {
    void chooseDestinationAndMove(Animal animal);
}
