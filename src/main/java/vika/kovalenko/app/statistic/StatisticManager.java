package vika.kovalenko.app.statistic;

import vika.kovalenko.app.core.manage.handler.SerializableHandler;

public interface StatisticManager extends SerializableHandler {
    void saveInitStatistic();

    boolean initStatisticNotExist();

    void cancelDayStatistic();

    void incrementDayStatistic(StatisticName statisticName);

    void incrementDayStatistic(StatisticName statisticName, int amount);

    int getCurrentDay();

    String getTotalItemsCountStatistic();

    String getUserStatistic(int choice);
}
