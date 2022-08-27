package game;

import lombok.Getter;
import model.abstraction.Animal;
import model.settings.GameField;
import resources.Action;
import service.GameObject;
import resources.GameObjectName;
import service.FindAppProperties;
import service.IslandRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static resources.GameObjectName.HERB;

public class IslandGame {
    @Getter
    private GameField gameField;
    @Getter
    private GameObject gameObject;
    private IslandRandom RANDOM;
    private int x;
    private int y;
    private List<ArrayList<? extends Animal>> currentAreaList;
    private Map<GameObjectName, ArrayList<?>> currentAreaMap;

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
                    currentAreaList = Collections.unmodifiableList(gameField.getCurrentAnimalsArea(y, x));
                    currentAreaMap = gameField.getCurrentAreaMap(y, x);
                    executeAction(action);
                }
            }
        }
    }

    private void executeAction(Action action) {
        switch (action) {
            case MOVE_ALL_ANIMALS -> {
                currentAreaList
                        .stream()
                        .iterator()
                        .forEachRemaining(this::animalsToMove);

                currentAreaList
                        .stream()
                        .iterator()
                        .forEachRemaining(animals -> animals
                                .removeIf(animal -> animal.itMoved));
            }
            case MATCH_ALL_ANIMALS -> {
                currentAreaList
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
        }
    }

    private void findPairToReproduce(int y, int x) {
        var areaMapEntry = gameField.getCurrentAreaMap(y, x).entrySet();
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
                    System.out.println(entry.getKey() + " to Paried " + animalsPairs.size() +
                                       "---------------- PAIRS = " + pairsNumber);

                    matchAnimals(entry.getKey(), pairsNumber, y, x);
                }

                //------------------------
                //    System.out.println("=============== waiting to be born : " + waitingToBeBorn.size());
            }
        }
    }

    private void matchAnimals(GameObjectName animalName, int pairsNumber, int y, int x) {
        int totalEmbryosNumber = 0;
        int embryosNumber;

        while (pairsNumber > 0) {
            embryosNumber = RANDOM.probableNumberOfYoung(animalName);
            System.out.println("-------------- embryos of " + animalName + " = " + embryosNumber);
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
            embryosNumber = FindAppProperties.getInstance()
                                    .getMaxCountOnArea(animalName) - parentArea.size();
        }

        System.out.println(animalName + " " + y + " " + x + " Number before birth = " + parentArea.size());

        while (embryosNumber > 0) {
            gameObject.addToArea(parentArea, gameObject.createNewIslandObject(animalName, y, x, true));
            embryosNumber--;
        }

        System.out.println(animalName + " " + y + " " + x + " Number after birth = " + parentArea.size());
    }

    private void animalsToMove(ArrayList<? extends Animal> animals) {
        for (Animal animal : animals) {
            moveAnimal(animal, animal.getClassKey());
        }
    }

    private <T extends Animal> void moveAnimal(T animal, GameObjectName animalName) {
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

    private boolean currentNumberLessThanMax(GameObjectName objectName, int currentCount) {
        int maxCountOnArea = FindAppProperties.getInstance().getMaxCountOnArea(objectName);
        return currentCount < maxCountOnArea;
    }

    private <T extends Animal> Map<GameObjectName, ArrayList<?>> getAreaMap(T animal) {
        return gameField.getCurrentAreaMap(animal.getY(), animal.getX());
    }
}
