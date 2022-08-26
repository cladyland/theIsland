package model.abstraction;

import lombok.Getter;
import lombok.Setter;
import resources.MoveDirection;
import service.IslandRandom;

public abstract class Animal extends BasicItem implements Movable, Reproducible {
    private final int MAX_SATURATION = 100;
    @Getter
    @Setter
    private boolean isYoung;
    protected int saturation;
    public boolean isAlive;
    public boolean itMoved;
    public boolean itParied;

    public Animal(int y, int x, boolean isYoung) {
        super(y, x);
        this.isYoung = isYoung;
        isAlive = true;
        itMoved = false;
        itParied = false;
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

    @Override
    public void reproduce() {
        IslandRandom random = new IslandRandom();
        itParied = random.toMate();
    }
}
