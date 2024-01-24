package vika.kovalenko.app.core.utility.util;

public final class MathUtil {
    public static final int MAX_PERCENT = 100;

    private MathUtil() {
    }

    public static int calculatePercentage(double obtained, double total) {
        double percentage = obtained * MAX_PERCENT / total;
        return (int) percentage;
    }

    public static double calculatePercentageOfTarget(int percent, double target) {
        return target * percent / MAX_PERCENT;
    }

    public static <T extends Number> boolean lessOrEqualsZero(T number) {
        return toDouble(number) <= 0;
    }

    public static <T extends Number> boolean equals(T number1, T number2) {
        return toDouble(number1) == toDouble(number2);
    }

    public static <T extends Number> boolean lessThan(T number1, T number2) {
        return toDouble(number1) < toDouble(number2);
    }

    public static <T extends Number> boolean lessOrEquals(T number1, T number2) {
        return toDouble(number1) <= toDouble(number2);
    }

    public static <T extends Number> boolean moreThan(T number1, T number2) {
        return toDouble(number1) > toDouble(number2);
    }

    public static <T extends Number> boolean moreOrEquals(T number1, T number2) {
        return toDouble(number1) >= toDouble(number2);
    }

    public static <T extends Number> boolean isPositive(T number1) {
        return toDouble(number1) > 0;
    }

    public static <T extends Number> double toDouble(T number) {
        return number.doubleValue();
    }

    public static double toDouble(String number) {
        return Double.parseDouble(number);
    }

    public static int toInt(String number) {
        return Integer.parseInt(number);
    }
}
