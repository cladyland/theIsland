package game;

import static resources.Action.EATING_ALL_ANIMALS;
import static resources.Action.MATCH_ALL_ANIMALS;
import static resources.Action.MOVE_ALL_ANIMALS;
import static resources.Action.REPRODUCE_ALL_ANIMALS;
import static resources.GameObjectName.HERB;

public class Game {
    private final IslandGame island;

    public Game(){
        island = new IslandGame();
        island.createGame();
    }

    void startGame() {
        startDay();
    }

    void drawIsland(){
        island.getGameField().printIsland();
    }

    private void startDay() {
        System.out.println("Day starts:");
        countAnimals();
        System.out.println("===============");
        island.chooseAction(MOVE_ALL_ANIMALS);
        island.chooseAction(EATING_ALL_ANIMALS);
        System.out.println("The animals ate:");
        countAnimals();
        System.out.println("===============");
        island.chooseAction(MATCH_ALL_ANIMALS);
        island.chooseAction(REPRODUCE_ALL_ANIMALS);
        System.out.println("The animals reproduced:");
        countAnimals();
        System.out.println("===============");
    }

    private void countAnimals() {
        int shallowWater = 2;
        int width = island.getGameField().getISLAND_WIDTH() + shallowWater;
        int height = island.getGameField().getISLAND_HEIGHT() + shallowWater;
        int animalCount = 0;
        int herbCount = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean isShallowWater = x == 0 || y == 0
                                         || x > island.getGameField().getISLAND_WIDTH()
                                         || y > island.getGameField().getISLAND_HEIGHT();
                if (!isShallowWater) {
                    var areaMap = island.getGameField().getCurrentAreaMap(y, x);
                    animalCount += areaMap
                            .entrySet()
                            .stream()
                            .filter(entry -> !entry.getKey().equals(HERB))
                            .mapToInt(entry -> entry.getValue().size()).sum();

                    herbCount += areaMap
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getKey().equals(HERB))
                            .mapToInt(entry -> entry.getValue().size()).sum();
                }
            }
        }

        System.out.println("animals count = " + animalCount);
        System.out.println("plants count = " + herbCount);
    }
}
