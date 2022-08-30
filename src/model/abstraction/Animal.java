package model.abstraction;

import lombok.Getter;
import lombok.Setter;
import resources.MoveDirection;
import service.AppProperties;
import service.IslandRandom;

import java.util.LinkedHashMap;

import static resources.KeysProperties.MAX;
import static resources.KeysProperties.SATURATION;

public abstract class Animal extends BasicItem implements Edible, Movable, Reproducible {
    @Getter
    @Setter
    private boolean isYoung;
    private double saturation;
    @Getter
    protected boolean isPredator;
    public boolean isAlive;
    public boolean itMoved;
    public boolean itParied;

    public Animal(int y, int x, boolean isYoung) {
        super(y, x);
        this.isYoung = isYoung;
        isAlive = true;
        itMoved = false;
        itParied = false;
        saturation = maxSaturation();
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
    public void eat(double amountOfFood) {
        if (saturation + amountOfFood <= maxSaturation()) {
            this.saturation += amountOfFood;
        } else {
            this.saturation = maxSaturation();
        }
    }

    @Override
    public void reproduce() {
        IslandRandom random = new IslandRandom();
        itParied = random.toMate();
    }

    public double getSaturation() {
        return saturation;
    }

    public LinkedHashMap<String, Integer> getRation(){
        return AppProperties.getInstance().getAnimalRation().get(classKey.toString());
    }

    private double maxSaturation(){
        return Double.parseDouble(AppProperties.getInstance()
                .getAppProperty(classKey, MAX, SATURATION));
    }

}
