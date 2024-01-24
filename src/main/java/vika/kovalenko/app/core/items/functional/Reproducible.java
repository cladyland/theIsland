package vika.kovalenko.app.core.items.functional;

import vika.kovalenko.app.core.items.Animal;

@FunctionalInterface
public interface Reproducible {
    <T extends Animal> T giveBirth();
}
