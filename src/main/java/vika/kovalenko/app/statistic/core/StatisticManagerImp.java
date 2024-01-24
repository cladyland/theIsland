package vika.kovalenko.app.statistic.core;

import vika.kovalenko.app.core.utility.util.SerializeUtil;
import vika.kovalenko.app.statistic.StatisticManager;
import vika.kovalenko.app.statistic.StatisticName;
import vika.kovalenko.app.statistic.UserStatisticName;

import java.util.Arrays;

import static java.util.Objects.isNull;
import static vika.kovalenko.app.primary.manage.constants.ConfigFilePath.INIT_STATISTIC_SERIALIZE;
import static vika.kovalenko.app.primary.manage.constants.ConfigFilePath.USER_STATISTIC_SERIALIZE;

public class StatisticManagerImp implements StatisticManager {
    private final StatisticCalculator CALCULATOR = new StatisticCalculator();
    private DayStatistic dayStatistic = new DayStatistic();
    private StatisticHandler statisticHandler = new StatisticHandler();

    @Override
    public void saveInitStatistic() {
        statisticHandler.fillInitStatistic(CALCULATOR.calculateAllItemsOnIsland());
    }

    @Override
    public boolean initStatisticNotExist() {
        return isNull(statisticHandler.initStatistic);
    }

    @Override
    public void cancelDayStatistic() {
        dayStatistic.cancelDayStatistic();
    }

    @Override
    public void incrementDayStatistic(StatisticName statisticName) {
        dayStatistic
                .defineStatistic(statisticName)
                .incrementAndGet();
    }

    @Override
    public void incrementDayStatistic(StatisticName statisticName, int amount) {
        dayStatistic
                .defineStatistic(statisticName)
                .addAndGet(amount);
    }

    @Override
    public int getCurrentDay() {
        return DayStatistic.currentDay.get();
    }

    @Override
    public String getTotalItemsCountStatistic() {
        return statisticHandler
                .createStrOfTotalCount(CALCULATOR.calculateAllItemsOnIsland());
    }

    @Override
    public String getUserStatistic(int choice) {
        switch (UserStatisticName.getStatisticName(choice)) {
            case GENERAL -> {
                return createGeneralStatString();
            }
            case ITEMS_COUNT -> {
                return getTotalItemsCountStatistic();
            }
            case ITEMS_DIFFERENCE -> {
                return getTotalItemsCountStatisticDifference();
            }
        }
        return null;
    }

    private String createGeneralStatString() {
        var builder = new StringBuilder();

        Arrays.stream(StatisticName.values())
                .forEach(statisticName -> builder
                        .append(getStatistic(statisticName))
                        .append("\n"));

        return builder.toString();
    }

    private String getStatistic(StatisticName statisticName) {
        int count = dayStatistic.defineStatistic(statisticName).get();
        return statisticName.getValue() + " " + count;
    }

    private String getTotalItemsCountStatisticDifference() {
        return statisticHandler
                .compareAndGetItemsCountDiff(CALCULATOR.calculateAllItemsOnIsland());
    }

    @Override
    public void serialize() {
        SerializeUtil.serialize(USER_STATISTIC_SERIALIZE, dayStatistic);
        SerializeUtil.serialize(INIT_STATISTIC_SERIALIZE, statisticHandler);
    }

    @Override
    public void deserialize() {
        dayStatistic = dayStatistic.deserialize();
        statisticHandler = (StatisticHandler) SerializeUtil.deserialize(INIT_STATISTIC_SERIALIZE);
    }
}
