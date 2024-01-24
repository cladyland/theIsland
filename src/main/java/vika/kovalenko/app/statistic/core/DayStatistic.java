package vika.kovalenko.app.statistic.core;

import vika.kovalenko.app.core.utility.util.SerializeUtil;
import vika.kovalenko.app.statistic.StatisticName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static vika.kovalenko.app.primary.manage.constants.ConfigFilePath.USER_STATISTIC_SERIALIZE;

class DayStatistic implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected static AtomicInteger currentDay = new AtomicInteger();
    private final AtomicInteger currentDaySave = currentDay;
    private final AtomicInteger[] statToCancel;
    protected AtomicInteger pregnantAnimals = new AtomicInteger();
    protected AtomicInteger animalsBorn = new AtomicInteger();
    protected AtomicInteger animalsKilled = new AtomicInteger();
    protected AtomicInteger animalsDeadOfHungary = new AtomicInteger();
    protected AtomicInteger caterpillarEggs = new AtomicInteger();
    protected AtomicInteger caterpillarPupae = new AtomicInteger();
    protected AtomicInteger caterpillarsEaten = new AtomicInteger();
    protected AtomicInteger plantsEaten = new AtomicInteger();

    {
        statToCancel = new AtomicInteger[]{pregnantAnimals, animalsBorn, animalsKilled,
                animalsDeadOfHungary, caterpillarEggs, caterpillarPupae, caterpillarsEaten, plantsEaten};
    }

    protected void cancelDayStatistic() {
        int initValue = 0;
        Arrays.stream(statToCancel)
                .forEach(statistic -> statistic.set(initValue));
    }

    protected AtomicInteger defineStatistic(StatisticName statisticName) {
        return switch (statisticName) {
            case DAYS -> currentDay;
            case PREGNANT -> pregnantAnimals;
            case BORN -> animalsBorn;
            case DIED_OF_HUNGER -> animalsDeadOfHungary;
            case HUNTED -> animalsKilled;
            case CATERPILLAR_EGGS -> caterpillarEggs;
            case CATERPILLAR_PUPAE -> caterpillarPupae;
            case CATERPILLARS_EATEN -> caterpillarsEaten;
            case PLANTS_EATEN -> plantsEaten;
        };
    }

    protected DayStatistic deserialize() {
        var saved = (DayStatistic) SerializeUtil.deserialize(USER_STATISTIC_SERIALIZE);
        currentDay = saved.currentDaySave;
        return saved;
    }
}
