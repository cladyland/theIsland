package game;

import lombok.Getter;
import model.abstraction.AbleToHunt;
import model.abstraction.Animal;
import model.herb.Herb;
import model.settings.GameField;
import resources.Action;
import service.GameObject;
import resources.GameObjectName;
import service.AppProperties;
import service.IslandRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static resources.GameObjectName.HERB;
import static resources.KeysProperties.MAX;
import static resources.KeysProperties.SPEED;

public class IslandGame {
    @Getter
    private GameField gameField;
    @Getter
    private GameObject gameObject;
    private IslandRandom RANDOM;
    private int x;
    private int y;
    private Map<GameObjectName, ArrayList<?>> currentAreaMap;
    private List<ArrayList<? extends Animal>> currentAreaAnimal;
    private List<ArrayList<Herb>> currentAreaHerbs;

    public IslandGame() {
    }

    public void createGame() {
        gameField = new GameField();
        gameField.createIslandMap();
        gameObject = new GameObject();
        RANDOM = new IslandRandom();
    }

    public void chooseAction(Action action) {
        for (y = 0; y < gameField.getISLAND_HEIGHT(); y++) {
            for (x = 0; x < gameField.getISLAND_WIDTH(); x++) {
                boolean isShallowWater = x == 0 || y == 0
                                         || x > gameField.getISLAND_WIDTH()
                                         || y > gameField.getISLAND_HEIGHT();
                if (!isShallowWater) {
                    currentAreaMap = gameField.getCurrentAreaMap(y, x);
                    currentAreaAnimal = Collections
                            .unmodifiableList(gameField.getIslandObjectsAreaList(y, x, "ANIMAL"));
                    currentAreaHerbs = gameField.getIslandObjectsAreaList(y, x, HERB);
                    executeAction(action);
                }
            }
        }
    }

    private void executeAction(Action action) {
        switch (action) {
            case MOVE_ALL_ANIMALS -> {
                currentAreaAnimal
                        .stream()
                        .iterator()
                        .forEachRemaining(this::animalsToMove);

                currentAreaAnimal
                        .stream()
                        .iterator()
                        .forEachRemaining(animals -> animals
                                .removeIf(animal -> animal.itMoved));
            }
            case MATCH_ALL_ANIMALS -> {
                currentAreaAnimal
                        .stream()
                        .iterator()
                        .forEachRemaining(animals -> animals.forEach(Animal::reproduce));

                findPairToReproduce(y, x);
            }
            case REPRODUCE_ALL_ANIMALS -> {
                gameField
                        .getEmbryosAreaMap(y, x)
                        .forEach((key, value) ->
                                birth(key, value, gameObject
                                        .convertListToAnimalList(currentAreaMap.get(key))));
            }
            case EATING_ALL_ANIMALS -> {
                currentAreaAnimal
                        .stream()
                        .iterator()
                        .forEachRemaining(animals -> animals.forEach(this::eating));

                utilize();
            }
        }
    }

    private void animalsToMove(ArrayList<? extends Animal> animals) {
        for (Animal animal : animals) {
            int maxAnimalSpeed = gameObject
                    .convertPropertyToInt(AppProperties.getInstance()
                            .getAppProperty(animal.getClassKey(), MAX, SPEED));
            if (maxAnimalSpeed > 0) {
                moveAnimal(animal, animal.getClassKey());
            }
        }
    }

    private void moveAnimal(Animal animal, GameObjectName animalName) {
        animal.move(RANDOM.directionMovement(), RANDOM.animalSpeed(animalName));
        boolean canMove = !animal.itMoved
                          && animal.getX() > 0
                          && animal.getY() > 0
                          && animal.getX() < gameField.getISLAND_WIDTH()
                          && animal.getY() < gameField.getISLAND_HEIGHT();

        if (canMove) {
            var toArea = getAreaMap(animal);
            int currentCount = toArea.get(animalName).size();

            if (currentNumberLessThanMax(animalName, currentCount)) {
                gameObject.addToArea(toArea.get(animalName), animal);
                animal.itMoved = true;
            }
        }
    }

    private void eating(Animal animal) {
        double receivedFood = findFood(animal.getRation(), animal);
        animal.eat(receivedFood);
    }

    private double findFood(LinkedHashMap<String, Integer> ration, Animal animal) {
        double foodWeight = 0;
        Object food = currentAreaMap
                .entrySet()
                .stream()
                .filter(entry -> ration.containsKey(entry.getKey().toString().toLowerCase())
                                 && entry.getValue().size() > 0)
                .map(Map.Entry::getValue)
                .map(foodMenu -> foodMenu.get(RANDOM.selectObjectToEat(foodMenu.size())))
                .findAny()
                .orElse(null);

        if (food != null) {
            foodWeight = eatFood(food.getClass(), food, animal);
        }
        return foodWeight;
    }

    private double eatFood(Class<?> foodClass, Object food, Animal animal) {
        double foodWeight = 0;
        if (AbleToHunt.class.isAssignableFrom(animal.getClass()) && !Herb.class.equals(foodClass)) {
            if (((Animal) food).isAlive) {
                int eatingProbability;
                var hunter = (AbleToHunt) animal;
                var prey = (Animal) food;
                eatingProbability = animal.getRation().get(prey.getClassKey().toString().toLowerCase());

                boolean huntingSuccess = hunter.hunting(eatingProbability);
                if (huntingSuccess) {
                    foodWeight = AppProperties.getInstance().getWeight(prey.getClassKey());
                    prey.isAlive = false;
                }
            }
        } else {
            if (!((Herb) food).isEaten) {
                var herb = (Herb) food;
                foodWeight = AppProperties.getInstance().getWeight(herb.getClassKey());
                herb.isEaten = true;
            }
        }
        return foodWeight;
    }

    private void utilize() {
        currentAreaAnimal
                .stream()
                .iterator()
                .forEachRemaining(animals -> animals
                        .removeIf(animal -> !animal.isAlive));

        currentAreaHerbs
                .stream()
                .iterator()
                .forEachRemaining(herbs -> herbs.removeIf(herb -> herb.isEaten));
    }

    private void findPairToReproduce(int y, int x) {
        List<? extends Animal> animalsPairs;
        int pairsNumber;

        for (var entry : gameField.getCurrentAreaMap(y, x).entrySet()) {
            if (!entry.getKey().equals(HERB)) {
                animalsPairs = gameObject
                        .convertListToAnimalList(entry.getValue())
                        .stream()
                        .filter(animal -> animal.itParied)
                        .toList();

                if (animalsPairs.size() % 2 != 0) {
                    animalsPairs.get(0).itParied = false;
                }
                pairsNumber = animalsPairs.size() / 2;
                if (pairsNumber > 0) {
                    matchAnimals(entry.getKey(), pairsNumber, y, x);
                }
            }
        }
    }

    private void matchAnimals(GameObjectName animalName, int pairsNumber, int y, int x) {
        int totalEmbryosNumber = 0;
        int embryosNumber;

        while (pairsNumber > 0) {
            embryosNumber = RANDOM.probableNumberOfYoung(animalName);
            totalEmbryosNumber += embryosNumber;
            pairsNumber--;
        }

        gameField
                .getEmbryosAreaMap(y, x)
                .put(animalName, totalEmbryosNumber);
    }

    private <T> void birth(GameObjectName animalName, int embryosNumber, ArrayList<T> parentArea) {
        int animalsNumberWithYoung = parentArea.size() + embryosNumber;
        if (!currentNumberLessThanMax(animalName, animalsNumberWithYoung)) {
            embryosNumber = AppProperties.getInstance()
                                    .getMaxCountOnArea(animalName) - parentArea.size();
        }
        while (embryosNumber > 0) {
            gameObject.addToArea(parentArea, gameObject.createNewIslandObject(animalName, y, x, true));
            embryosNumber--;
        }
    }

    private boolean currentNumberLessThanMax(GameObjectName objectName, int currentCount) {
        int maxCountOnArea = AppProperties.getInstance().getMaxCountOnArea(objectName);
        return currentCount < maxCountOnArea;
    }

    private <T extends Animal> Map<GameObjectName, ArrayList<?>> getAreaMap(T animal) {
        return gameField.getCurrentAreaMap(animal.getY(), animal.getX());
    }
}
