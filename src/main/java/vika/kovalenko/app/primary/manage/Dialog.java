package vika.kovalenko.app.primary.manage;

import vika.kovalenko.app.core.exceptions.UserInputException;
import vika.kovalenko.app.core.utility.util.MathUtil;
import vika.kovalenko.app.core.utility.util.PrinterUtil;
import vika.kovalenko.app.core.utility.util.SerializeUtil;
import vika.kovalenko.app.primary.manage.constants.AnsiColors;
import vika.kovalenko.app.primary.manage.constants.Messages;
import vika.kovalenko.app.primary.manage.util.ReaderUtil;
import vika.kovalenko.app.statistic.UserStatisticName;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static vika.kovalenko.app.core.manage.imp.Manager.GAME_MANAGER;
import static vika.kovalenko.app.core.manage.imp.Manager.STATISTIC_MANAGER;
import static vika.kovalenko.app.core.utility.util.PrinterUtil.print;
import static vika.kovalenko.app.core.utility.util.StringColorChanger.colourfulText;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.BLUE;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.BOLD;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.CYAN;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.GREEN;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.ITALIC;
import static vika.kovalenko.app.primary.manage.constants.Messages.ASTERISKS;
import static vika.kovalenko.app.primary.manage.constants.Messages.GAME_CONTINUATION_PROMPT;
import static vika.kovalenko.app.primary.manage.constants.Messages.GREETING;
import static vika.kovalenko.app.primary.manage.constants.Messages.CHOOSE_STATISTIC_TO_SHOW;
import static vika.kovalenko.app.primary.manage.constants.Messages.WRITE_NUMBER;
import static vika.kovalenko.app.primary.manage.constants.Messages.WRONG_INPUT;

public class Dialog {
    private final ExecutorService EXECUTOR;
    private final String STATISTIC_MENU_PROMPT;
    private final int RETURN_TO_MAIN_MENU;

    {
        EXECUTOR = Executors.newSingleThreadExecutor();
        RETURN_TO_MAIN_MENU = UserStatisticName.values().length + UserStatisticName.INDEX_CORRECTION;
        STATISTIC_MENU_PROMPT = CHOOSE_STATISTIC_TO_SHOW + "\n"
                                + RETURN_TO_MAIN_MENU + " - return to the main menu";
    }

    public Dialog() {
    }

    public void startDialog() {
        print(colourfulText(BLUE, ASTERISKS));
        initGame();

        if (STATISTIC_MANAGER.initStatisticNotExist()) {
            endTheGame(false);
        } else {
            continueGame();
        }
    }

    private void initGame() {
        if (SerializeUtil.savedFilesExisted()) {
            if (answerYes(Messages.LOAD_OR_INIT)) {
                GAME_MANAGER.loadGame();
                return;
            }
        }
        print(colourfulText(BOLD, GREEN, GREETING));
        print(colourfulText(BLUE, ASTERISKS));
        GAME_MANAGER.initGame();
    }

    private boolean answerYes(String message) {
        while (true) {
            print(message);
            switch (ReaderUtil.readNumber()) {
                case 1 -> {
                    return true;
                }
                case 2 -> {
                    return false;
                }
                default -> PrinterUtil.printError(WRONG_INPUT);
            }
        }
    }

    private void continueGame() {
        do {
            ReaderUtil.cleanReaderBuffer();
            print(colourfulText(ITALIC, CYAN, GAME_CONTINUATION_PROMPT));
            try {
                defineUserChoice(ReaderUtil.readNumber());
            } catch (UserInputException ex) {
                PrinterUtil.printError(WRITE_NUMBER);
            }
        } while (ReaderUtil.isReaderOpen());
    }

    private void defineUserChoice(int input) {
        switch (input) {
            case 1 -> startNewDay();
            case 2 -> statisticMenu();
            case 3 -> endTheGame(answerYes(Messages.SAVE_OR_NOT));
            default -> PrinterUtil.printError(WRONG_INPUT);
        }
    }

    private void startNewDay() {
        var future = CompletableFuture.runAsync(GAME_MANAGER::startNewDay, EXECUTOR);
        future.join();
    }

    private void statisticMenu() {
        while (true) {
            print(STATISTIC_MENU_PROMPT);
            try {
                int choice = ReaderUtil.readNumber();
                if (MathUtil.equals(choice, RETURN_TO_MAIN_MENU)) {
                    return;
                }
                print(STATISTIC_MANAGER.getUserStatistic(choice));
            } catch (UserInputException ex) {
                PrinterUtil.printError(ex.getMessage());
            }
        }
    }

    private void endTheGame(boolean saveGame) {
        ReaderUtil.closeReader();
        EXECUTOR.shutdownNow();
        GAME_MANAGER.endGame(saveGame);
        PrinterUtil.print(colourfulText(AnsiColors.MAGENTA, Messages.FAREWELL));
    }
}
