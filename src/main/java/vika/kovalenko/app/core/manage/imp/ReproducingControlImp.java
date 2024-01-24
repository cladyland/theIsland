package vika.kovalenko.app.core.manage.imp;

import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.items.Animal;
import vika.kovalenko.app.core.items.BasicItem;
import vika.kovalenko.app.core.manage.ReproducingControl;
import vika.kovalenko.app.core.manage.handler.EggsHandler;
import vika.kovalenko.app.core.utility.Coordinates;
import vika.kovalenko.app.core.utility.util.IslandRandom;
import vika.kovalenko.app.statistic.StatisticName;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.nonNull;
import static vika.kovalenko.app.core.constants.KeyProperty.MAX;
import static vika.kovalenko.app.core.constants.KeyProperty.YOUNG;
import static vika.kovalenko.app.core.manage.imp.Manager.ANIMAL_MANAGER;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_FIELD_MANAGER;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_SETTINGS;
import static vika.kovalenko.app.core.manage.imp.Manager.ITEM_FACTORY;
import static vika.kovalenko.app.core.manage.imp.Manager.LIFE_CYCLE_CONTROL;
import static vika.kovalenko.app.core.manage.imp.Manager.STATISTIC_MANAGER;
import static vika.kovalenko.app.core.utility.util.MathUtil.toInt;
import static vika.kovalenko.app.statistic.StatisticName.BORN;
import static vika.kovalenko.app.statistic.StatisticName.CATERPILLAR_EGGS;
import static vika.kovalenko.app.statistic.StatisticName.CATERPILLAR_PUPAE;
import static vika.kovalenko.app.statistic.StatisticName.PREGNANT;

class ReproducingControlImp implements ReproducingControl {

    @Override
    public void reproduce(Animal animal) {
        if (canReproduce(animal)) {
            var area = GAME_FIELD_MANAGER.getCurrentItemArea(animal.getCoordinates(), animal.getClassKey());
            Animal pair = findPair(new HashSet<>(area));
            mating(pair);
        }
    }

    private boolean canReproduce(Animal animal) {
        return animal.isAlive()
               && !animal.isYoung()
               && !animal.isPregnant();
    }

    private Animal findPair(Set<BasicItem> area) {
        return (Animal) area
                .stream()
                .filter(animal -> canReproduce((Animal) animal))
                .findAny()
                .orElse(null);
    }

    private void mating(Animal animal) {
        if (nonNull(animal) && IslandRandom.actionSuccessful()) {
            if (ANIMAL_MANAGER.isCaterpillar(animal.getClassKey())) {
                layEggs(animal);
            } else {
                animal.setPregnant(true);
                LIFE_CYCLE_CONTROL.addAnimalToQueue(animal);
                STATISTIC_MANAGER.incrementDayStatistic(PREGNANT);
            }
        }
    }

    private void layEggs(Animal animal) {
        int eggsCount = calculateFoetusCount(animal.getClassKey());
        var handler = new EggsHandler(animal.getClassKey(), animal.getCoordinates(), eggsCount);

        LIFE_CYCLE_CONTROL.addCaterpillarEggsToQueue(handler);
        STATISTIC_MANAGER.incrementDayStatistic(CATERPILLAR_EGGS, eggsCount);
    }

    private int calculateFoetusCount(GameObjectName animalName) {
        int maxYoung = maxYoungCount(animalName);
        return IslandRandom.getRandomNumber(maxYoung);
    }

    private int maxYoungCount(GameObjectName animalName) {
        return toInt(GAME_SETTINGS.getAppProperty(animalName, MAX, YOUNG));
    }

    @Override
    public void giveBirth(Animal animal) {
        if (animal.isAlive() && animal.isPregnant()) {
            int foetusCount = calculateFoetusCount(animal.getClassKey());
            engender(animal, foetusCount);
        }
    }

    private void engender(Animal mother, int foetusCount) {
        beBorn(mother.getCoordinates(), mother.getClassKey(), foetusCount, BORN, mother::giveBirth);
        mother.setPregnant(false);
    }

    private void beBorn(Coordinates coordinates, GameObjectName animalName, int count,
                        StatisticName statisticName, ItemHandler<Animal> bornMethod) {
        Set<BasicItem> area = getSyncArea(coordinates, animalName);

        for (; count > 0; count--) {
            if (notEnoughPlace(area.size(), animalName)) {
                return;
            }
            Animal young = bornMethod.handleItem();
            area.add(young);

            if (!ANIMAL_MANAGER.isCaterpillar(animalName)) {
                LIFE_CYCLE_CONTROL.addAnimalToQueue(young);
            }
            STATISTIC_MANAGER.incrementDayStatistic(statisticName);
        }
    }

    private Set<BasicItem> getSyncArea(Coordinates coordinates, GameObjectName objectName) {
        return Collections.synchronizedSet(
                GAME_FIELD_MANAGER.getCurrentItemArea(coordinates, objectName));
    }

    private boolean notEnoughPlace(int animalsCount, GameObjectName animalName) {
        return !GAME_FIELD_MANAGER.isEnoughPlaceOnArea(animalsCount, animalName);
    }

    @Override
    public void hatching(EggsHandler handler) {
        beBorn(handler.coordinates(),
                handler.objectName(),
                handler.eggsCount(),
                CATERPILLAR_PUPAE,
                () -> createCaterpillarPupae(handler));
    }

    private Animal createCaterpillarPupae(EggsHandler handler) {
        var coordinates = handler.coordinates();

        return ITEM_FACTORY
                .createIslandItem(handler.objectName(), coordinates.getX(), coordinates.getY());
    }

    private interface ItemHandler<T extends Animal> {
        T handleItem();
    }
}
