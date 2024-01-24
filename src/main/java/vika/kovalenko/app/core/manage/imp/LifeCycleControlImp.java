package vika.kovalenko.app.core.manage.imp;

import lombok.AllArgsConstructor;
import vika.kovalenko.app.core.items.Animal;
import vika.kovalenko.app.core.items.Plant;
import vika.kovalenko.app.core.items.functional.Regenerative;
import vika.kovalenko.app.core.manage.LifeCycleControl;
import vika.kovalenko.app.core.manage.handler.EggsHandler;
import vika.kovalenko.app.core.utility.util.MathUtil;
import vika.kovalenko.app.core.utility.util.SerializeUtil;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_FIELD_MANAGER;
import static vika.kovalenko.app.core.manage.imp.Manager.REPRODUCING_CONTROL;
import static vika.kovalenko.app.core.manage.imp.Manager.STATISTIC_MANAGER;
import static vika.kovalenko.app.primary.manage.constants.ConfigFilePath.LIFE_CYCLE_SERIALIZE;

class LifeCycleControlImp implements LifeCycleControl {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int THREAD_COUNT = 6;
    private final Queue<Wrapper<Animal>> PREGNANT_ANIMALS = new LinkedBlockingQueue<>();
    private final Queue<Wrapper<Animal>> YOUNG_ANIMALS = new LinkedBlockingQueue<>();
    private final Queue<Wrapper<Animal>> REMAINS = new LinkedBlockingQueue<>();
    private final Queue<Wrapper<EggsHandler>> CATERPILLAR_EGGS = new LinkedBlockingQueue<>();
    private final Queue<Wrapper<Plant>> PLANTS_FOR_REGENERATE = new LinkedBlockingQueue<>();
    private transient List<Callable<Void>> tasks = new ArrayList<>();

    {
        fillTasks(PREGNANT_ANIMALS, REPRODUCING_CONTROL::giveBirth);
        fillTasks(YOUNG_ANIMALS, young -> young.setYoung(false));
        fillTasks(CATERPILLAR_EGGS, REPRODUCING_CONTROL::hatching);
        fillTasks(PLANTS_FOR_REGENERATE, Regenerative::regenerate);
    }

    private <T> void fillTasks(Queue<Wrapper<T>> itemQueue, ItemHandler<T> itemHandler) {
        tasks.add(() -> {
            processItemQueue(itemQueue, itemHandler);
            return null;
        });
    }

    @Override
    public void addAnimalToQueue(Animal animal) {
        if (!animal.isAlive()) {
            addToQueue(REMAINS, animal);
        } else if (animal.isYoung()) {
            addToQueue(YOUNG_ANIMALS, animal);
        } else if (animal.isPregnant()) {
            addToQueue(PREGNANT_ANIMALS, animal);
        }
    }

    private <T> void addToQueue(Queue<Wrapper<T>> queue, T item) {
        queue.add(new Wrapper<>(item, currentDay()));
    }

    private int currentDay() {
        return STATISTIC_MANAGER.getCurrentDay();
    }

    @Override
    public void addCaterpillarEggsToQueue(EggsHandler handler) {
        addToQueue(CATERPILLAR_EGGS, handler);
    }

    @Override
    public void addPlantToQueue(Plant plant) {
        addToQueue(PLANTS_FOR_REGENERATE, plant);
    }

    @Override
    public void processLifeCycle() {
        var executor = Executors.newFixedThreadPool(THREAD_COUNT);

        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdownNow();
        }
    }

    private <T> void processItemQueue(Queue<Wrapper<T>> itemQueue, ItemHandler<T> itemHandler) {
        while (true) {
            T item = getItemFromQueue(itemQueue);
            if (isNull(item)) {
                break;
            }
            itemHandler.handleItem(item);
        }
    }

    private <T> T getItemFromQueue(Queue<Wrapper<T>> queue) {
        Wrapper<T> wrapper = queue.poll();
        if (nonNull(wrapper) && MathUtil.lessThan(wrapper.day, currentDay())) {
            return wrapper.item;
        }
        return null;
    }

    @Override
    public void removeRemains() {
        REMAINS.forEach(remains -> removeRemainsFromArea(remains.item));
    }

    private void removeRemainsFromArea(Animal remains) {
        GAME_FIELD_MANAGER
                .getCurrentItemArea(remains.getCoordinates(), remains.getClassKey())
                .remove(remains);
    }

    @Override
    public void serialize() {
        SerializeUtil.serialize(LIFE_CYCLE_SERIALIZE, this);
    }

    @Override
    public void deserialize() {
        var saved = (LifeCycleControlImp) SerializeUtil.deserialize(LIFE_CYCLE_SERIALIZE);
        saved.tasks = this.tasks;
        Manager.LIFE_CYCLE_CONTROL = saved;
    }

    @AllArgsConstructor
    private static class Wrapper<T> implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        private T item;
        private int day;
    }

    private interface ItemHandler<T> {
        void handleItem(T item);
    }
}
