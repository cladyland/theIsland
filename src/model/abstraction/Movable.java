package model.abstraction;

import resources.MoveDirection;

public interface Movable {
    void move(MoveDirection direction, int speed);
}
