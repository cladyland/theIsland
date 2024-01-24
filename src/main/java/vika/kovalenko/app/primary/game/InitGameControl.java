package vika.kovalenko.app.primary.game;

import vika.kovalenko.app.core.constants.KeyProperty;
import vika.kovalenko.app.core.exceptions.UserInputException;
import vika.kovalenko.app.core.utility.util.MathUtil;
import vika.kovalenko.app.core.utility.util.PrinterUtil;
import vika.kovalenko.app.primary.manage.constants.Messages;
import vika.kovalenko.app.primary.manage.util.ReaderUtil;

import java.util.List;
import java.util.function.IntPredicate;

import static vika.kovalenko.app.core.constants.KeyProperty.FIELD;
import static vika.kovalenko.app.core.constants.KeyProperty.GAME;
import static vika.kovalenko.app.core.constants.KeyProperty.LENGTH;
import static vika.kovalenko.app.core.constants.KeyProperty.WIDTH;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_FIELD_MANAGER;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_SETTINGS;
import static vika.kovalenko.app.core.manage.imp.Manager.STATISTIC_MANAGER;
import static vika.kovalenko.app.core.utility.util.MathUtil.toInt;
import static vika.kovalenko.app.core.utility.util.StringColorChanger.colourfulText;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.BLUE;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.BOLD;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.ITALIC;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.MAGENTA;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.YELLOW;
import static vika.kovalenko.app.primary.manage.constants.Messages.ASTERISKS;
import static vika.kovalenko.app.primary.manage.constants.Messages.CHOOSE_INIT_MODE;
import static vika.kovalenko.app.primary.manage.constants.Messages.CHOSE_DEFAULT_SETTINGS;
import static vika.kovalenko.app.primary.manage.constants.Messages.CHOSE_MANUAL_SETTINGS;
import static vika.kovalenko.app.primary.manage.constants.Messages.MIN_AND_MAX_ISLAND_SIZE;
import static vika.kovalenko.app.primary.manage.constants.Messages.USER_ISLAND_CREATED;
import static vika.kovalenko.app.primary.manage.constants.Messages.WRONG_INPUT;

class InitGameControl {
    private final int MIN_ISLAND_SIZE = 5;
    private final int MAX_ISLAND_SIZE = 30;

    public void chooseModeAndCreateIsland() {
        switch (chooseCreationMode()) {
            case 1 -> createNewDefaultIsland();
            case 2 -> createNewUserIsland();
            case 3 -> {
                return;
            }
        }
        STATISTIC_MANAGER.saveInitStatistic();
    }

    private int chooseCreationMode() {
        PrinterUtil.print(CHOOSE_INIT_MODE);
        return getUserInput(CHOOSE_INIT_MODE, this::modeIsCorrect);
    }

    private boolean modeIsCorrect(int userChoice) {
        var validModes = List.of(1, 2, 3);

        return validModes.stream()
                .anyMatch(mode -> mode == userChoice);
    }

    private int getUserInput(String message, IntPredicate validation) {
        while (true) {
            try {
                int userChoice = ReaderUtil.readNumber();

                if (validation.test(userChoice)) {
                    return userChoice;
                } else {
                    throw new UserInputException(WRONG_INPUT);
                }
            } catch (UserInputException ex) {
                PrinterUtil.printError(ex.getMessage());
                PrinterUtil.print(message);
            }
        }
    }

    private void createNewDefaultIsland() {
        String choice = colourfulText(BOLD, YELLOW, CHOSE_DEFAULT_SETTINGS);
        String message = "\n" + choice + USER_ISLAND_CREATED;

        createNewIsland(message, getDefaultIslandSize(LENGTH), getDefaultIslandSize(WIDTH));
    }

    private int getDefaultIslandSize(KeyProperty param) {
        return toInt(GAME_SETTINGS.getAppProperty(GAME, FIELD, param));
    }

    private void createNewIsland(String message, int length, int width) {
        GAME_FIELD_MANAGER.createAndFillNewIsland(length, width);

        String colorAsterisks = colourfulText(MAGENTA, ASTERISKS);
        String fullMessage = colorAsterisks + message +
                             STATISTIC_MANAGER.getTotalItemsCountStatistic() + colorAsterisks;

        PrinterUtil.printFormat(fullMessage, length, width);
    }

    private void createNewUserIsland() {
        var info = String.format(CHOSE_MANUAL_SETTINGS, MIN_ISLAND_SIZE, MIN_ISLAND_SIZE, MAX_ISLAND_SIZE, MAX_ISLAND_SIZE);
        PrinterUtil.print(colourfulText(BOLD, YELLOW, info));

        int length = setIslandSize(LENGTH.name().toLowerCase());
        int width = setIslandSize(WIDTH.name().toLowerCase());

        createNewIsland(USER_ISLAND_CREATED, length, width);
    }

    private int setIslandSize(String sizeName) {
        PrinterUtil.printFormat(Messages.SPECIFY_SIZE, sizeName);

        String colourfulText = colourfulText(ITALIC, BLUE, MIN_AND_MAX_ISLAND_SIZE);
        var errorMessage = String.format(colourfulText, sizeName, MIN_ISLAND_SIZE, MAX_ISLAND_SIZE);

        return getUserInput(errorMessage, this::sizeIsCorrect);
    }

    private boolean sizeIsCorrect(int size) {
        return MathUtil.moreOrEquals(size, MIN_ISLAND_SIZE)
               && MathUtil.lessOrEquals(size, MAX_ISLAND_SIZE);
    }
}
