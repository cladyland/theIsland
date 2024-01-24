package vika.kovalenko.app.core.items.functional;

import vika.kovalenko.app.core.utility.Coordinates;

@FunctionalInterface
public interface Movable {
    void move(Coordinates coordinates);
}
