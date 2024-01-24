package vika.kovalenko.app.statistic;

import lombok.AllArgsConstructor;
import vika.kovalenko.app.core.exceptions.UserInputException;

import java.util.Arrays;

import static vika.kovalenko.app.primary.manage.constants.Messages.WRONG_INPUT;

@AllArgsConstructor
public enum UserStatisticName {
    GENERAL("general day statistic"),
    ITEMS_COUNT("number of all island objects"),
    ITEMS_DIFFERENCE("items count difference from start to now");

    public static final int INDEX_CORRECTION = 1;
    private final String VALUE;

    public static UserStatisticName getStatisticName(int index) {
        try {
            return values()[index - INDEX_CORRECTION];
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new UserInputException(WRONG_INPUT);
        }
    }

    public static String getStatisticListWithOrdinals() {
        return createListWithNamesAndOrdinals();
    }

    private static String createListWithNamesAndOrdinals() {
        var stringBuilder = new StringBuilder();
        UserStatisticName lastEnum = values()[values().length - INDEX_CORRECTION];

        Arrays.stream(values())
                .forEach(item -> {
                    String str = item.ordinal() + INDEX_CORRECTION + " - " + item.VALUE;
                    if (item != lastEnum) {
                        str += "\n";
                    }
                    stringBuilder.append(str);
                });

        return stringBuilder.toString();
    }
}
