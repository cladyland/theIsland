package vika.kovalenko.app.primary.game;

import vika.kovalenko.app.core.items.Animal;
import vika.kovalenko.app.core.items.BasicItem;
import vika.kovalenko.app.core.utility.util.MathUtil;
import vika.kovalenko.app.core.utility.util.PrinterUtil;
import vika.kovalenko.app.primary.manage.constants.Messages;
import vika.kovalenko.app.statistic.StatisticName;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

import static vika.kovalenko.app.core.manage.imp.Manager.ANIMAL_MANAGER;
import static vika.kovalenko.app.core.manage.imp.Manager.STATISTIC_MANAGER;
import static vika.kovalenko.app.core.utility.util.StringColorChanger.colourfulText;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.BLUE;
import static vika.kovalenko.app.primary.manage.constants.AnsiColors.ITALIC;

class GameControl {

    public void startNewDay(Set<BasicItem> animals) {
        STATISTIC_MANAGER.incrementDayStatistic(StatisticName.DAYS);
        live(animals);
    }

    private void printDayProgressInfo(int day, String arg) {
        String info = String.format(Messages.THE_DAY, day, arg);
        PrinterUtil.print(colourfulText(ITALIC, BLUE, info));
    }

    private void live(Set<BasicItem> animals) {
        int threadCount = 10;
        var executor = Executors.newFixedThreadPool(threadCount);
        int day = STATISTIC_MANAGER.getCurrentDay();

        synchronized (Collections.synchronizedSet(new HashSet<>(animals))) {
            printDayProgressInfo(day, "started...");

            animals.parallelStream().forEach(animal -> {
                int minActions = 3;
                while (MathUtil.isPositive(minActions)) {
                    executor.execute(() -> ANIMAL_MANAGER.chooseAndPerformAction((Animal) animal));
                    minActions--;
                }
            });
        }
        executor.shutdownNow();
        printDayProgressInfo(day, "ended");
    }
}
