package vika.kovalenko.app.core.manage.imp;

import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.items.Animal;
import vika.kovalenko.app.core.items.BasicItem;
import vika.kovalenko.app.core.items.Herbivorous;
import vika.kovalenko.app.core.items.Plant;
import vika.kovalenko.app.core.items.functional.AbleToHunt;
import vika.kovalenko.app.core.manage.EatingControl;
import vika.kovalenko.app.core.utility.util.IslandRandom;
import vika.kovalenko.app.core.utility.util.MathUtil;
import vika.kovalenko.app.statistic.StatisticName;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static vika.kovalenko.app.core.constants.GameObjectName.CATERPILLAR;
import static vika.kovalenko.app.core.constants.GameObjectName.HERB;
import static vika.kovalenko.app.core.manage.imp.Manager.ANIMAL_MANAGER;
import static vika.kovalenko.app.core.manage.imp.Manager.LIFE_CYCLE_CONTROL;
import static vika.kovalenko.app.core.manage.imp.Manager.STATISTIC_MANAGER;
import static vika.kovalenko.app.core.utility.util.MathUtil.MAX_PERCENT;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_FIELD_MANAGER;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_SETTINGS;

class EatingControlImp implements EatingControl {

    @Override
    public void findAndEatFood(Animal animal) {
        var area = new HashMap<>(GAME_FIELD_MANAGER.getCurrentAreaMap(animal.getCoordinates()));

        if (animal instanceof AbleToHunt) {
            chooseEatingWay(animal, area);
        } else {
            grazing(animal, area);
        }
    }

    private void chooseEatingWay(Animal animal, Map<GameObjectName, Set<BasicItem>> area) {
        Map<GameObjectName, Integer> ration = GAME_SETTINGS.getANIMALS_RATION().get(animal.getClassKey());
        GameObjectName priorityFoodType = IslandRandom.chooseFoodType(ration.keySet());

        switch (priorityFoodType) {
            case HERB -> grazing(animal, area);
            case CATERPILLAR -> foraging(animal, area);
            default -> hunting(animal, area, ration);
        }
    }

    private void grazing(Animal animal, Map<GameObjectName, Set<BasicItem>> area) {
        searchForFood(animal, area, HERB);
    }

    private void foraging(Animal animal, Map<GameObjectName, Set<BasicItem>> area) {
        searchForFood(animal, area, CATERPILLAR);
    }

    private void searchForFood(Animal animal, Map<GameObjectName, Set<BasicItem>> area, GameObjectName foodName) {
        Set<BasicItem> foodSet = area.get(foodName);
        boolean isPlant = isPlant(foodName);

        Iterator<BasicItem> foodIterator = foodSet.iterator();

        while (aliveAndHungry(animal) && foodIterator.hasNext()) {
            BasicItem food = foodIterator.next();

            if (!food.isAlive() || !IslandRandom.actionSuccessful()) {
                continue;
            }

            eatFood(animal, food);

            if (isPlant) {
                checkIfPlantCompletelyEaten((Plant) food);
            } else {
                deadItemManage(food, StatisticName.CATERPILLARS_EATEN);
            }
        }
    }

    private boolean isPlant(GameObjectName objectName) {
        return objectName.equals(HERB);
    }

    private boolean aliveAndHungry(Animal animal) {
        return animal.isAlive()
               && MathUtil.lessThan(animal.getSatietyPercent(), MAX_PERCENT);
    }

    private void eatFood(Animal animal, BasicItem food) {
        double ate = animal.eat(food.getEdibleWeight());
        food.reduceWeight(ate);
    }

    private void checkIfPlantCompletelyEaten(Plant plant) {
        if (plant.onlyInediblePartRemained()) {
            deadItemManage(plant, StatisticName.PLANTS_EATEN);
        }
    }

    private void deadItemManage(BasicItem item, StatisticName statisticName) {
        item.setAlive(false);
        STATISTIC_MANAGER.incrementDayStatistic(statisticName);

        if (isPlant(item.getClassKey())) {
            LIFE_CYCLE_CONTROL.addPlantToQueue((Plant) item);
        } else {
            LIFE_CYCLE_CONTROL.addAnimalToQueue((Animal) item);
        }
    }

    private void hunting(Animal hunter, Map<GameObjectName, Set<BasicItem>> area, Map<GameObjectName, Integer> ration) {
        Set<BasicItem> hunterMenu = createHunterMenu(area, ration.keySet());
        BasicItem food = choosePrey(hunterMenu);

        boolean huntSuccessful = false;

        if (nonNull(food)) {
            int huntingSuccessProbability = ration.get(food.getClassKey());
            huntSuccessful = tryHunting(hunter, (Animal) food, huntingSuccessProbability);
        }

        if (!huntSuccessful && aliveAndHungry(hunter)) {
            if (hunter instanceof Herbivorous) {
                grazing(hunter, area);
            } else {
                food = findRemains(hunterMenu);
                if (nonNull(food)) {
                    eatFood(hunter, food);
                }
            }
        }
    }

    private Set<BasicItem> createHunterMenu(Map<GameObjectName, Set<BasicItem>> area, Set<GameObjectName> foodNames) {
        return area
                .entrySet()
                .stream()
                .filter(entry -> isValidFoodType(entry, foodNames))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private boolean isValidFoodType(Map.Entry<GameObjectName, Set<BasicItem>> entry, Set<GameObjectName> foodNames) {
        GameObjectName foodType = entry.getKey();

        return !isPlant(foodType)
               && !ANIMAL_MANAGER.isCaterpillar(foodType)
               && foodNames.contains(foodType)
               && !entry.getValue().isEmpty();
    }

    private BasicItem choosePrey(Set<BasicItem> menu) {
        var menuList = menu
                .stream()
                .filter(BasicItem::isAlive)
                .toList();

        return menuList.isEmpty() ? null : menuList.get(IslandRandom.getRandomNumber(menuList.size()));
    }

    private boolean tryHunting(Animal hunter, Animal food, int huntingSuccessProbability) {
        boolean huntingSuccess = ((AbleToHunt) hunter).hunt(huntingSuccessProbability);

        if (huntingSuccess) {
            deadItemManage(food, StatisticName.HUNTED);
            eatFood(hunter, food);
            return true;
        }
        return false;
    }

    private Animal findRemains(Set<BasicItem> menu) {
        return (Animal) menu
                .stream()
                .filter(animal -> !animal.isAlive() && !animal.onlyInediblePartRemained())
                .findAny()
                .orElse(null);
    }
}
