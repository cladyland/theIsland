package vika.kovalenko.app.core.items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vika.kovalenko.app.core.items.functional.Edible;
import vika.kovalenko.app.core.items.functional.Movable;
import vika.kovalenko.app.core.utility.util.MathUtil;
import vika.kovalenko.app.core.utility.Coordinates;
import vika.kovalenko.app.core.items.functional.Reproducible;
import vika.kovalenko.app.statistic.StatisticName;

import static vika.kovalenko.app.core.constants.KeyProperty.MAX;
import static vika.kovalenko.app.core.constants.KeyProperty.SATIETY;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_SETTINGS;
import static vika.kovalenko.app.core.manage.imp.Manager.ITEM_FACTORY;
import static vika.kovalenko.app.core.manage.imp.Manager.STATISTIC_MANAGER;

@NoArgsConstructor
public abstract class Animal extends BasicItem implements Edible, Movable, Reproducible {
    private static final int SATIETY_REDUCTION_PERCENT = 10;
    @Getter
    @Setter
    protected boolean isYoung;
    @Getter
    private double satiety;
    private double maxSatiety;
    @Getter
    @Setter
    private boolean isPregnant;

    protected void initSatietyAndWeight() {
        initSatiety();
        initWeight();
    }

    private void initSatiety() {
        maxSatiety = MathUtil.toDouble(GAME_SETTINGS.getAppProperty(classKey, MAX, SATIETY));

        int initSatietyPercent = 99;
        satiety = MathUtil.calculatePercentageOfTarget(initSatietyPercent, maxSatiety);
    }

    @Override
    protected void initWeight() {
        super.initWeight();
        inedibleWeight = calculateInedibleWeight();
    }

    protected double calculateInedibleWeight() {
        int inediblePercentage = 5;
        return MathUtil.calculatePercentageOfTarget(inediblePercentage, weight);
    }

    @Override
    public void move(Coordinates coordinates) {
        setCoordinates(coordinates.getX(), coordinates.getY());
        reduceSaturation();
    }

    private void reduceSaturation() {
        satiety -= MathUtil.calculatePercentageOfTarget(SATIETY_REDUCTION_PERCENT, satiety);
        checkSatiety();
    }

    private void checkSatiety() {
        if (MathUtil.lessOrEqualsZero(satiety)) {
            isAlive = false;
            STATISTIC_MANAGER.incrementDayStatistic(StatisticName.DIED_OF_HUNGER);
        }
    }

    @Override
    public double eat(double foodWeight) {
        double foodAmount = calculateFoodAmount(foodWeight);
        satiety += foodAmount;

        return foodAmount;
    }

    private double calculateFoodAmount(double foodWeight) {
        double satietyDifference = maxSatiety - satiety;

        return Math.min(foodWeight, satietyDifference);
    }

    public int getSatietyPercent() {
        return MathUtil.calculatePercentage(satiety, maxSatiety);
    }

    @Override
    public <T extends Animal> T giveBirth() {
        return ITEM_FACTORY.createYoungAnimal(this);
    }
}
