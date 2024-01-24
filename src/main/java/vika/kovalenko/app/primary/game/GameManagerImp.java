package vika.kovalenko.app.primary.game;

import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.items.BasicItem;
import vika.kovalenko.app.core.manage.handler.SerializableHandler;
import vika.kovalenko.app.primary.manage.GameManager;
import vika.kovalenko.app.primary.manage.constants.Messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static vika.kovalenko.app.core.manage.imp.Manager.GAME_FIELD_MANAGER;
import static vika.kovalenko.app.core.manage.imp.Manager.LIFE_CYCLE_CONTROL;
import static vika.kovalenko.app.core.manage.imp.Manager.STATISTIC_MANAGER;
import static vika.kovalenko.app.core.utility.util.PrinterUtil.printFormat;

public class GameManagerImp implements GameManager {
    private final int THREAD_COUNT = 2;
    private final ExecutorService EXECUTOR = Executors.newFixedThreadPool(THREAD_COUNT);
    private final GameControl gameControl = new GameControl();
    private final SerializableHandler[] SERIALIZERS = {GAME_FIELD_MANAGER, LIFE_CYCLE_CONTROL, STATISTIC_MANAGER};

    @Override
    public void initGame() {
        new InitGameControl()
                .chooseModeAndCreateIsland();
    }

    @Override
    public void loadGame() {
        printFormat(Messages.USER_PROGRESS_WAIT, "loading");
        performHandlersActions(SERIALIZERS, SerializableHandler::deserialize);
    }

    private void performHandlersActions(SerializableHandler[] handlers, GameProgressHandler action) {
        Arrays.stream(handlers)
                .forEach(handler -> performAction(action, handler));
    }

    private void performAction(GameProgressHandler action, SerializableHandler handler) {
        action.perform(handler);
    }

    @Override
    public void startNewDay() {
        STATISTIC_MANAGER.cancelDayStatistic();

        try {
            EXECUTOR.invokeAll(createTasks());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            LIFE_CYCLE_CONTROL.removeRemains();
        }
    }

    private List<Callable<Void>> createTasks() {
        Set<BasicItem> animalsSet = createAllAnimalsSet();
        List<Callable<Void>> tasks = new ArrayList<>();

        tasks.add(() -> {
            gameControl.startNewDay(animalsSet);
            return null;
        });

        tasks.add(() -> {
            LIFE_CYCLE_CONTROL.processLifeCycle();
            return null;
        });

        return tasks;
    }

    private Set<BasicItem> createAllAnimalsSet() {
        Set<BasicItem> animals = new HashSet<>();

        for (int y = 0; y < GAME_FIELD_MANAGER.getIslandLength(); y++) {
            for (int x = 0; x < GAME_FIELD_MANAGER.getIslandWidth(); x++) {
                if (GAME_FIELD_MANAGER.isShallowWater(x, y)) {
                    continue;
                }
                animals.addAll(getAnimals(x, y));
            }
        }
        return animals;
    }

    private Set<BasicItem> getAnimals(int x, int y) {
        return GAME_FIELD_MANAGER.getCurrentAreaMap(x, y)
                .entrySet()
                .stream()
                .filter(entry -> !entry.getKey().equals(GameObjectName.HERB))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public void endGame(boolean saveProgress) {
        if (saveProgress) {
            printFormat(Messages.USER_PROGRESS_WAIT, "saving");
            performHandlersActions(SERIALIZERS, SerializableHandler::serialize);
        }
        EXECUTOR.shutdownNow();
    }

    @FunctionalInterface
    interface GameProgressHandler {
        void perform(SerializableHandler handler);
    }
}
