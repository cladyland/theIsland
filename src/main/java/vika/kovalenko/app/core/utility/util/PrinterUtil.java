package vika.kovalenko.app.core.utility.util;

import static vika.kovalenko.app.primary.manage.constants.AnsiColors.RED;

public final class PrinterUtil {

    private PrinterUtil() {
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static void printFormat(String message, Object... args) {
        System.out.printf(message, args);
        System.out.println();
    }

    public static void printError(String message) {
        String errorMessage = StringColorChanger.colourfulText(RED, message);
        System.out.println(errorMessage);
    }
}
