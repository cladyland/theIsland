package vika.kovalenko.app.core.utility.util;

import vika.kovalenko.app.primary.manage.constants.AnsiColors;

public class StringColorChanger {

    private StringColorChanger() {
    }

    public static String colourfulText(String color, String text) {
        return color + text + AnsiColors.RESET;
    }

    public static String colourfulText(String style, String color, String text) {
        return colourfulText(color, style + text);
    }
}
