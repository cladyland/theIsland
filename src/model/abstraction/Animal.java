package model.abstraction;

import lombok.Getter;
import lombok.Setter;
import resources.MoveDirection;

public abstract class Animal extends BasicItem implements Movable {
    private final int MAX_SATURATION = 100;
    @Getter
    @Setter
    private boolean isYoung;
    protected int saturation;
    public boolean isAlive;
    public boolean itMoved;

    public Animal(int y, int x, boolean isYoung) {
        super(y, x);
        this.isYoung = isYoung;
        isAlive = true;
        itMoved = false;
        saturation = MAX_SATURATION;
    }

    @Override
    public void move(MoveDirection direction, int speed) {
        switch (direction) {
            case UP -> setY(getY() - speed);
            case DOWN -> setY(getY() + speed);
            case LEFT -> setX(getX() - speed);
            case RIGHT -> setX(getX() + speed);
        }
    }
}
