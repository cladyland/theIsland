package model.settings;

import lombok.Getter;
import model.herb.Herb;
import model.herbivorous.Boar;
import model.herbivorous.Buffalo;
import model.herbivorous.Caterpillar;
import model.herbivorous.Deer;
import model.herbivorous.Duck;
import model.herbivorous.Goat;
import model.herbivorous.Horse;
import model.herbivorous.Mouse;
import model.herbivorous.Rabbit;
import model.herbivorous.Sheep;
import model.predator.Bear;
import model.predator.Boa;
import model.predator.Eagle;
import model.predator.Fox;
import model.predator.Wolf;
import resources.GameObjectName;
import resources.KeysProperties;
import service.AppProperties;
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
                    String emoja = AppProperties.getInstance().getAppProperty(GameObjectName.HERB, KeysProperties.EMOJI);
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
                    String emoja = AppProperties.getInstance().getAppProperty(GameObjectName.HERB, KeysProperties.EMOJI);
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
    public <T, E> List<ArrayList<T>> getIslandObjectsAreaList(int y, int x, E objectName){
        return ((Map<GameObjectName, ArrayList<T>>) gameField[y][x])
                .entrySet()
                .stream()
                .filter(entry -> (objectName == HERB) == entry.getKey().equals(HERB))
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
            basicArea.put(GameObjectName.BOA, new ArrayList<Boa>());
            basicArea.put(GameObjectName.FOX, new ArrayList<Fox>());
            basicArea.put(GameObjectName.BEAR, new ArrayList<Bear>());
            basicArea.put(GameObjectName.EAGLE, new ArrayList<Eagle>());
            basicArea.put(GameObjectName.HORSE, new ArrayList<Horse>());
            basicArea.put(GameObjectName.DEER, new ArrayList<Deer>());
            basicArea.put(GameObjectName.RABBIT, new ArrayList<Rabbit>());
            basicArea.put(GameObjectName.MOUSE, new ArrayList<Mouse>());
            basicArea.put(GameObjectName.GOAT, new ArrayList<Goat>());
            basicArea.put(GameObjectName.SHEEP, new ArrayList<Sheep>());
            basicArea.put(GameObjectName.BOAR, new ArrayList<Boar>());
            basicArea.put(GameObjectName.BUFFALO, new ArrayList<Buffalo>());
            basicArea.put(GameObjectName.DUCK, new ArrayList<Duck>());
            basicArea.put(GameObjectName.CATERPILLAR, new ArrayList<Caterpillar>());
            basicArea.put(GameObjectName.HERB, new ArrayList<Herb>());
        }
    }
}
