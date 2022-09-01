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
    protected double saturation;
    public boolean isAlive;
    public boolean itMoved;
    public boolean itParied;

    public Animal(int y, int x, boolean isYoung) {
        super(y, x);
        this.isYoung = isYoung;
        isAlive = true;
        itMoved = false;
        itParied = false;
    }

    @Override
    public void move(MoveDirection direction, int speed) {
        switch (direction) {
            case UP -> setY(getY() - speed);
            case DOWN -> setY(getY() + speed);
            case LEFT -> setX(getX() - speed);
            case RIGHT -> setX(getX() + speed);
        }
        reduceSaturation();
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
        reduceSaturation();
    }

    public void reduceSaturation() {
        double tenPercent = saturation * 0.1;
        saturation -= tenPercent;
        if (saturation <= 0 && maxSaturation() != 0) {
            isAlive = false;
        }
    }

    public String getSaturation() {
        int maxSaturationPercent = 100;
        double currentSaturation = 0;
        if (maxSaturation() != 0){
            currentSaturation = saturation * maxSaturationPercent / maxSaturation();
        }
        return currentSaturation + "%";
    }

    public LinkedHashMap<String, Integer> getRation() {
        return AppProperties.getInstance().getAnimalRation().get(classKey.toString());
    }

    protected double maxSaturation() {
        return Double.parseDouble(AppProperties.getInstance()
                .getAppProperty(classKey, MAX, SATURATION));
    }
}
