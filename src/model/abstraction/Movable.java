package model.abstraction;

import resources.MoveDirection;

@FunctionalInterface
public interface Movable {
    void move(MoveDirection direction, int speed);
}
