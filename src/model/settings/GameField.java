package model.settings;

import lombok.Getter;
import model.abstraction.Animal;
import model.abstraction.BasicItem;
import model.abstraction.Plant;
import model.herb.Herb;
import model.predator.Fox;
import model.predator.Wolf;
import resources.KeysProperties;
import service.FindAppProperties;
import service.IslandRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static resources.KeysProperties.WOLF;

public class GameField {
    @Getter
    private final int ISLAND_HEIGHT;
    @Getter
    private final int ISLAND_WIDTH;
    private final IslandRandom RANDOM;
    private Object[][] gameField;
    private int x;
    private int y;

    public GameField() {
        GameFieldSettings gameFieldSettings = new GameFieldSettings();
        ISLAND_HEIGHT = gameFieldSettings.getHeight();
        ISLAND_WIDTH = gameFieldSettings.getWidth();
        RANDOM = new IslandRandom();
    }

    public void createIslandMap() {
        int shallowWater = 2;
        gameField = new Object[ISLAND_HEIGHT + shallowWater][ISLAND_WIDTH + shallowWater];
        for (y = 0; y < gameField.length; y++) {
            for (x = 0; x < gameField[y].length; x++) {
                boolean isShallowWater = x == 0 || y == 0 || x > ISLAND_WIDTH || y > ISLAND_HEIGHT;
                if (isShallowWater) {
                    gameField[y][x] = true;
                } else {
                    gameField[y][x] = Area.getBasicAreaMap();
                }
            }
        }
        initialized();
    }

    public void test() {
        printIsland();

        var areaMap = getCurrentAreaMap(1, 1);
        areaMap.get(WOLF).add(getNewAnimal());
        var myArea = areaMap.get(WOLF);

        System.out.println("Size of 2 3 map = " + areaMap.size());
        System.out.println("Wolf size = " + areaMap.get(WOLF).size());
        Wolf wolf = (Wolf) areaMap.get(WOLF).get(0);
        wolf.move(RANDOM.directionMovement(), RANDOM.animalSpeed(WOLF));
        var areaMap2 = getCurrentAreaMap(wolf.getY(), wolf.getX());
        areaMap.get(WOLF).remove(wolf);
        areaMap2.get(WOLF).add(addingWolf(wolf));

        System.out.println("AFTER MOVING");
        System.out.println("y = " + wolf.getY() + " x = " + wolf.getX());
        System.out.println("WOLF " + areaMap.get(WOLF));

        printIsland();
    }

    public void printIsland() {
        for (Object[] objects : gameField) {
            for (Object object : objects) {
                if (object instanceof HashMap) {
                    String emoja = FindAppProperties.getAppProperty(KeysProperties.HERB, KeysProperties.EMOJI);
                    //   System.out.print(emoja + " ");
                    System.out.print(object + " ");
                } else {
                    //    System.out.print("\uD83C\uDF0A" + " ");
                    System.out.print(object + " ");
                }
            }
            System.out.println();
        }
    }

    private void initialized() {
        for (y = 0; y < gameField.length; y++) {
            for (x = 0; x < gameField[y].length; x++) {
                if (gameField[y][x] instanceof Map) {
                    var areaMap = getCurrentAreaMap(y, x);
                    areaMap.replaceAll((key, value) -> RANDOM.createInitialObjects(key, value, y, x));
                }
            }
        }
    }

    public <T> T getNewAnimal() {
        Wolf wolf = new Wolf(1, 1);
        return (T) wolf;
    }

    public <T> T addingWolf(Wolf wolf) {
        return (T) wolf;
    }

    @SuppressWarnings("unchecked")
    public Map<KeysProperties, ArrayList<? extends Animal>> getCurrentAreaMap(int y, int x) {
        return (Map<KeysProperties, ArrayList<? extends Animal>>) gameField[y][x];
    }

    private static class Area {
        private static Map<KeysProperties, ArrayList<?>> basicArea;

        public static Map<KeysProperties, ArrayList<?>> getBasicAreaMap() {
            fillBasicAreaMap();
            return new HashMap<>(basicArea);
        }

        private static void fillBasicAreaMap() {
            basicArea = new HashMap<>();
            basicArea.put(WOLF, new ArrayList<Wolf>());
            //      area.put(KeysProperties.BOA, new ArrayList<Boa>());
        //    basicArea.put(KeysProperties.FOX, new ArrayList<Fox>());
      /*  area.put(KeysProperties.BEAR, new ArrayList<Bear>());
        area.put(KeysProperties.EAGLE, new ArrayList<Eagle>());
        area.put(KeysProperties.HORSE, new ArrayList<Horse>());
        area.put(KeysProperties.DEER, new ArrayList<Deer>());
        area.put(KeysProperties.RABBIT, new ArrayList<Rabbit>());
        area.put(KeysProperties.MOUSE, new ArrayList<Mouse>());
        area.put(KeysProperties.GOAT, new ArrayList<Goat>());
        area.put(KeysProperties.SHEEP, new ArrayList<Sheep>());
        area.put(KeysProperties.BOAR, new ArrayList<Boar>());
        area.put(KeysProperties.BUFFALO, new ArrayList<Buffalo>());
        area.put(KeysProperties.DUCK, new ArrayList<Duck>());
        area.put(KeysProperties.CATERPILLAR, new ArrayList<Caterpillar>());*/
   //     basicPlant.put(KeysProperties.HERB, new ArrayList<Herb>());
        }
    }
}
