package game;

import lombok.Getter;
import model.abstraction.Animal;
import model.settings.GameField;
import model.settings.GameObject;
import resources.KeysProperties;
import service.FindAppProperties;
import service.IslandRandom;

import java.util.ArrayList;
import java.util.Map;

import static resources.KeysProperties.COUNT;
import static resources.KeysProperties.HERB;
import static resources.KeysProperties.MAX;

public class IslandGame {
    @Getter
    private GameField gameField;
    @Getter
    private GameObject gameObject;
    private IslandRandom RANDOM;
    private int x;
    private int y;

    public IslandGame() {
    }

    public void createGame() {
        gameField = new GameField();
        gameField.createIslandMap();
        gameObject = new GameObject();
        RANDOM = new IslandRandom();
    }

    public void moveAllAnimals() {
        for (y = 0; y < gameField.getISLAND_HEIGHT(); y++) {
            for (x = 0; x < gameField.getISLAND_WIDTH(); x++) {
                boolean isShallowWater = x == 0 || y == 0
                                         || x > gameField.getISLAND_WIDTH()
                                         || y > gameField.getISLAND_HEIGHT();

                if (!isShallowWater) {
                    var currentAreaMap = gameField.getCurrentAreaMap(y, x);
                    currentAreaMap
                            .entrySet()
                            .stream()
                            .filter(entry -> !entry.getKey().equals(HERB))
                            .map(Map.Entry::getValue)
                            .iterator()
                            .forEachRemaining(this::animalsToMove);

                }

/*                ArrayList<? extends Animal> currentArea;
                if (!isShallowWater) {
                    currentArea = gameObject.getAnimalAreaListByKey(gameField.getCurrentAreaMap(y, x), WOLF);
                    currentArea.forEach(animal -> {
                        moves(animal, WOLF);
                    });
                    currentArea.removeIf(animal -> animal.itMoved);
                }*/
            }
        }
    }

    private void animalsToMove(ArrayList<? extends Animal> animals){
        for (Animal animal : animals) {
            moveAnimal(animal, animal.getClassKey());
        }
    }

    private <T extends Animal> void moveAnimal(T animal, KeysProperties key) {
        animal.move(RANDOM.directionMovement(), RANDOM.animalSpeed(key));
        boolean canMove = animal.getX() > 0
                          && animal.getY() > 0
                          && animal.getX() < gameField.getISLAND_WIDTH()
                          && animal.getY() < gameField.getISLAND_HEIGHT();

        if (canMove) {
            var toArea = getAnimalArea(animal);
            int maxCountOnArea = Integer.parseInt(FindAppProperties.getAppProperty(key, MAX, COUNT));
            int currentCount = toArea.get(key).size();
            if (currentCount < maxCountOnArea) {
                gameObject.addToArea(toArea.get(key), animal);
                animal.itMoved = true;
            }
        }
    }

    private <T extends Animal> Map<KeysProperties, ArrayList<? extends Animal>> getAnimalArea(T animal) {
        return gameField.getCurrentAreaMap(animal.getY(), animal.getX());
    }
}
