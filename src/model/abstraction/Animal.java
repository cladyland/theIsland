package model.abstraction;

import resources.KeysProperties;

public abstract class Animal extends BasicItem implements Movable {
    protected final int MAX_SATURATION = 100;
    protected int saturation;
    public boolean isAlive;
    public boolean itMoved;

    public Animal(int y, int x) {
        super(y, x);
        itMoved = false;
    }

    @Override
    public void move(KeysProperties direction, int speed) {
        switch (direction) {
            case UP -> setY(getY() - speed);
            case DOWN -> setY(getY() + speed);
            case LEFT -> setX(getX() - speed);
            case RIGHT -> setX(getX() + speed);
            default -> System.out.println("the animal could not choose the direction of movement");
        }
    }
}
