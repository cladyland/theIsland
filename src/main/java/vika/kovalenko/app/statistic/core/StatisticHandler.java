package vika.kovalenko.app.statistic.core;

import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.constants.KeyProperty;
import vika.kovalenko.app.core.utility.util.StringColorChanger;
import vika.kovalenko.app.primary.manage.constants.AnsiColors;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static vika.kovalenko.app.core.manage.imp.Manager.GAME_SETTINGS;
import static vika.kovalenko.app.core.utility.util.StringColorChanger.colourfulText;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.BLUE;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.ITALIC;

class StatisticHandler implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected Map<GameObjectName, Integer> initStatistic;

    protected void fillInitStatistic(Map<GameObjectName, Integer> initStatistic) {
        this.initStatistic = initStatistic;
    }

    protected String compareAndGetItemsCountDiff(Map<GameObjectName, Integer> statistic) {
        var result = new HashMap<GameObjectName, String>();
        statistic
                .forEach((key, value) -> result.put(key, calculateAndFormatDifference(key, value)));

        return createStrOfTotalCount(result);
    }

    private String calculateAndFormatDifference(GameObjectName name, int val) {
        int diff = val - initStatistic.get(name);
        String color = (diff == 0) ? "" : (diff > 0) ? AnsiColors.GREEN : AnsiColors.RED;

        return StringColorChanger.colourfulText(color, diff + "");
    }

    protected <T> String createStrOfTotalCount(Map<GameObjectName, T> itemsCount) {
        var builder = new StringBuilder();
        itemsCount
                .forEach((key, value) -> builder.append(convertTotalCountEntry(key, value)));

        return builder.toString();
    }

    private <T> String convertTotalCountEntry(GameObjectName key, T value) {
        String pattern = "%s %s %s \n";
        String emoji = GAME_SETTINGS.getAppProperty(key, KeyProperty.EMOJI);
        String name = colourfulText(ITALIC, BLUE, key.toString().toLowerCase() + ":");

        return String.format(pattern, emoji, name, value);
    }
}
