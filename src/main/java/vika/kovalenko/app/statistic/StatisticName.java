package vika.kovalenko.app.statistic;

import lombok.Getter;

import static vika.kovalenko.app.core.utility.util.StringColorChanger.colourfulText;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.ITALIC;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.YELLOW;

@Getter
public enum StatisticName {
    DAYS("the Island age (days)"),
    PREGNANT("pregnant animals"),
    BORN("newborn animals"),
    DIED_OF_HUNGER("died of hunger"),
    HUNTED("hunted"),
    CATERPILLAR_EGGS("caterpillars eggs"),
    CATERPILLAR_PUPAE("caterpillars pupae"),
    CATERPILLARS_EATEN("caterpillars eaten"),
    PLANTS_EATEN("plants eaten");

    private final String value;

    StatisticName(String value) {
        this.value = colourfulText(ITALIC, YELLOW, value);
    }
}
