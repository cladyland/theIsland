package model.settings;

import model.abstraction.BasicItem;
import model.predator.Fox;
import model.predator.Wolf;
import resources.KeysProperties;
import service.IslandRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameField {
    private final int ISLAND_HEIGHT;
    private final int ISLAND_WIDTH;
    private final IslandRandom RANDOM;
    private Object[][] gameField;
    private int x;
    private int y;

    public GameField() {
        ISLAND_HEIGHT = GameFieldSettings.getHeight();
        ISLAND_WIDTH = GameFieldSettings.getWidth();
        RANDOM = new IslandRandom();
    }

    public static void main(String[] args) {
        GameField gameField = new GameField();
        gameField.initialized();
    }

    public void initialized() {
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
        for (y = 0; y < gameField.length; y++) {
            for (x = 0; x < gameField[y].length; x++) {
                if (gameField[y][x] instanceof Map) {
                    var areaMap = getCurrentAreaMap(y, x);
                    for (var key : areaMap.entrySet()) {
                        areaMap.put(key.getKey(), RANDOM.createInitialObjects(key.getKey(), key.getValue(), y, x));
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Map<KeysProperties, ArrayList<? extends BasicItem>> getCurrentAreaMap(int y, int x){
        return (Map<KeysProperties, ArrayList<? extends BasicItem>>) gameField[y][x];
    }

    private static class Area {
        private static Map<KeysProperties, ArrayList<? extends BasicItem>> basicArea;

        public static Map<KeysProperties, ArrayList<? extends BasicItem>> getBasicAreaMap() {
            fillBasicAreaMap();
            return new HashMap<>(basicArea);
        }

        private static void fillBasicAreaMap() {
            basicArea = new HashMap<>();
            basicArea.put(KeysProperties.WOLF, new ArrayList<Wolf>());
            //      area.put(KeysProperties.BOA, new ArrayList<Boa>());
            basicArea.put(KeysProperties.FOX, new ArrayList<Fox>());
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
        area.put(KeysProperties.CATERPILLAR, new ArrayList<Caterpillar>());
        area.put(KeysProperties.HERB, new ArrayList<Herb>());*/
        }
    }
}
