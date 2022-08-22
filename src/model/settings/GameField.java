package model.settings;

import model.abstraction.BasicItem;
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
    private final int ISLAND_HEIGHT;
    private final int ISLAND_WIDTH;
    private final GameObject GAME_OBJECT;
    private final IslandRandom RANDOM;
    private Object[][] gameField;
    private int x;
    private int y;

    public GameField() {
        GameFieldSettings gameFieldSettings = new GameFieldSettings();
        ISLAND_HEIGHT = gameFieldSettings.getHeight();
        ISLAND_WIDTH = gameFieldSettings.getWidth();
        GAME_OBJECT = new GameObject();
        RANDOM = new IslandRandom();
    }

    //метод main тут временно ^_^
    public static void main(String[] args) {
        GameField gameField = new GameField();
        gameField.initialized();
    }

    public void initialized() {
        //тут я создаю карту острова с дополнительными границами
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
        //добавление животных на карту
        for (y = 0; y < gameField.length; y++) {
            for (x = 0; x < gameField[y].length; x++) {
                if (gameField[y][x] instanceof Map) {
                    var areaMap = getCurrentAreaMap(y, x);
                    areaMap.replaceAll((key, value) -> RANDOM.createInitialObjects(key, value, y, x));
                }
            }
        }

        //дальше тестовый код (позже будет удалено). Пожалуйста, не обращайте внимание на то, что все написано в
        //методе initialized, я оставлю только верхние строчки кода
        // ------------------------------------------------------
        //следующий цикл отвечает за публикацию массива. Закоментированные строки отвечают за графику. Сейчас они
        //мне не нужны, так как я не смогу увидеть, что происходит на моем острове :)
        //P.S. позже я сделаю из этого отдельный метод, а пока мне нужна именно дубликация кода (не спрашивайте)
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

        //ПРЕДУПРЕЖДЕНИЕ: следуюшая часть кода (до цикла for) может быть сложной для понимания

        //тут я тестирую добавление/удаление объектов (волков), т.е. "перемещение"
        var areaMap = getCurrentAreaMap(1, 1);
        areaMap.get(WOLF).add(getNewAnimal());
        var myArea = areaMap.get(WOLF);

        //проверка общего метода (будет использовано вместо "areaMap.get(WOLF).add(getNewAnimal())")
        GAME_OBJECT.addToArea(myArea, new Wolf(1, 1));

        //проверка наличия волков и их количества...
        System.out.println("Size of 2 3 map = " + areaMap.size());
        System.out.println("Wolf size = " + areaMap.get(WOLF).size());
        //тестовый волк
        Wolf wolf = (Wolf) areaMap.get(WOLF).get(0);
        //рандомный выбор перемещения волка (направление + количество клеток рандомно от максимальной скорости)
        wolf.move(RANDOM.directionMovement(), RANDOM.animalSpeed(wolf.getClass().getSimpleName()));
        //определение области, куда волк будет перемещен
        var areaMap2 = getCurrentAreaMap(wolf.getY(), wolf.getX());
        //временные методы, в дальнейшем будут использованы методы класса GameObject
        areaMap.get(WOLF).remove(wolf);
        areaMap2.get(WOLF).add(addingWolf(wolf));

        System.out.println("AFTER MOVING");
        System.out.println("y = " + wolf.getY() + " x = " + wolf.getX());
        System.out.println("WOLF " + areaMap.get(WOLF));

        //снова публикация объектов острова, но уже после перемещения волка (да, тот же код, ну "мне так надо" :) )
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

    //следующие два метода будут удалены, поскольку я создала их аналоги, которые могут работать с любым объектом
    //пока что я их оставила только для того, чтобы работала тестовая часть кода выше
    public <T> T getNewAnimal() {
        Wolf wolf = new Wolf(1, 1);
        return (T) wolf;
    }

    public <T> T addingWolf(Wolf wolf) {
        return (T) wolf;
    }

    //----------------------------------------------
    //конец тестового кода

    //следующий метод я хотела вынести либо во внутренний класс Area, либо в класс GameObject, но не знаю, как
    //заменить gameField[y][x], чтобы метод работал, так что оставила как есть
    @SuppressWarnings("unchecked")
    public Map<KeysProperties, ArrayList<? extends BasicItem>> getCurrentAreaMap(int y, int x) {
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
            basicArea.put(WOLF, new ArrayList<Wolf>());
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
