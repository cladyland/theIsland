package model.settings;

import lombok.Getter;
import model.herb.Herb;
import model.predator.Fox;
import model.predator.Wolf;
import resources.GameObjectName;
import resources.KeysProperties;
import service.FindAppProperties;
import service.IslandRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static resources.GameObjectName.HERB;

public class GameField {
    @Getter
    private final int ISLAND_HEIGHT;
    @Getter
    private final int ISLAND_WIDTH;
    private final IslandRandom RANDOM;
    private Object[][] gameField;
    private Object[][] embryosField;
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
        createEmbryosField();
    }

    public void printIsland() {
        for (Object[] objects : gameField) {
            for (Object object : objects) {
                if (object instanceof HashMap) {
                    String emoja = FindAppProperties.getInstance().getAppProperty(GameObjectName.HERB, KeysProperties.EMOJI);
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

    public void printEmbryosMap() {
        for (Object[] objects : embryosField) {
            for (Object object : objects) {
                if (object instanceof HashMap) {
                    String emoja = FindAppProperties.getInstance().getAppProperty(GameObjectName.HERB, KeysProperties.EMOJI);
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

    @SuppressWarnings("unchecked")
    public Map<GameObjectName, ArrayList<?>> getCurrentAreaMap(int y, int x) {
        return (Map<GameObjectName, ArrayList<?>>) gameField[y][x];
    }

    @SuppressWarnings("unchecked")
    public <T> List<ArrayList<T>> getCurrentAnimalsArea(int y, int x) {
        var result = (Map<GameObjectName, ArrayList<T>>) gameField[y][x];
        return result
                .entrySet()
                .stream()
                .filter(entry -> !entry.getKey().equals(HERB))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public HashMap<GameObjectName, Integer> getEmbryosAreaMap(int y, int x) {
        return (HashMap<GameObjectName, Integer>) embryosField[y][x];
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

    private void createEmbryosField() {
        embryosField = new Object[getISLAND_HEIGHT()][getISLAND_WIDTH()];
        for (int y = 0; y < embryosField.length; y++) {
            for (int x = 0; x < embryosField[y].length; x++) {
                embryosField[y][x] = new HashMap<GameObjectName, Integer>();
            }
        }
    }

    private static class Area {
        private static Map<GameObjectName, ArrayList<?>> basicArea;

        public static Map<GameObjectName, ArrayList<?>> getBasicAreaMap() {
            fillBasicAreaMap();
            return new HashMap<>(basicArea);
        }

        private static void fillBasicAreaMap() {
            basicArea = new HashMap<>();
            basicArea.put(GameObjectName.WOLF, new ArrayList<Wolf>());
            //      area.put(KeysProperties.BOA, new ArrayList<Boa>());
            basicArea.put(GameObjectName.FOX, new ArrayList<Fox>());
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
            //    basicArea.put(GameObjectName.HERB, new ArrayList<Herb>());
        }
    }
}
