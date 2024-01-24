package vika.kovalenko.app.primary.manage.constants;

import vika.kovalenko.app.statistic.UserStatisticName;

import static vika.kovalenko.app.core.utility.util.StringColorChanger.colourfulText;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.BLUE;

public final class Messages {
    private static final String YES_NO = """
            1 - yes
            2 - no""";
    public static final String GREETING = """
            Welcome to the Island game!
            ---------------------------------------------------------------------------------------------
            To start the game you need to create an island using the default setting or manual settings.
            After that, you can start a new day, view the island statistics and finish the game.
            To select a menu item, enter the corresponding number and press the 'Enter' key.
            ---------------------------------------------------------------------------------------------
            Are you ready? Let's create your island!""";
    public static final String ASTERISKS = "**************************************************";
    public static final String SAVE_OR_NOT = "Save your progress before ending the game?\n" + YES_NO;
    public static final String LOAD_OR_INIT = "Welcome back! Load your progress?\n" + YES_NO;
    public static final String WRONG_INPUT = "wrong input";
    public static final String CHOOSE_INIT_MODE = """
            Please, choose how to create an island:
            1 - default settings,
            2 - manual settings,
            3 - exit""";
    public static final String USER_ISLAND_CREATED = """
            \nCongrats! Your island was successfully created with parameters:
            length = %d
            width = %d
            and the first inhabitants inhabited the island:
                        
            """;
    public static final String CHOSE_DEFAULT_SETTINGS = "You chose default settings...\n";
    public static final String CHOSE_MANUAL_SETTINGS = """
            You chose manual settings...
            Note of the island size:
            * minimum = %dx%d
            * maximum = %dx%d""";
    public static final String SPECIFY_SIZE = "please, specify " + colourfulText(BLUE, "%s") + " of your island:";
    public static final String WRITE_NUMBER = WRONG_INPUT + "\nplease, write a number";
    public static final String MIN_AND_MAX_ISLAND_SIZE = "minimum %s = %d, maximum = %d.\nPlease, choose a " +
                                                         "number from this range";
    public static final String GAME_CONTINUATION_PROMPT = """
            please, choose how to continue the game:
            1 - start a new day
            2 - open statistics menu
            3 - finish the game""";

    public static final String USER_PROGRESS_WAIT = "your progress is %s... please, wait";
    public static final String THE_DAY = "the day %d %s";
    public static final String CHOOSE_STATISTIC_TO_SHOW = "Please, choose what to show:\n" +
                                                          UserStatisticName.getStatisticListWithOrdinals();
    public static final String FAREWELL = "☻ see you later, bye ☻";
}
