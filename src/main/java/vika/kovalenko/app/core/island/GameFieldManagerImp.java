package vika.kovalenko.app.core.island;

import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.items.BasicItem;
import vika.kovalenko.app.core.utility.Coordinates;
import vika.kovalenko.app.core.utility.util.IslandRandom;
import vika.kovalenko.app.core.utility.util.MathUtil;
import vika.kovalenko.app.core.utility.util.SerializeUtil;

import java.util.Map;
import java.util.Set;

import static vika.kovalenko.app.core.manage.imp.Manager.ITEM_FACTORY;
import static vika.kovalenko.app.core.utility.util.MathUtil.lessOrEqualsZero;
import static vika.kovalenko.app.core.island.GameField.Area.getNewBasicAreaMap;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_FIELD_MANAGER;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_SETTINGS;
import static vika.kovalenko.app.primary.manage.constants.ConfigFilePath.GAME_FIELD_SERIALIZE;

public class GameFieldManagerImp implements GameFieldManager {
    private GameField gameField;
    private Object[][] islandMap;
    private int x;
    private int y;

    @Override
    public void createAndFillNewIsland(int length, int width) {
        gameField = new GameField(length, width);
        new GameFieldInit().createAndFill();
    }

    @Override
    public boolean isShallowWater(int x, int y) {
        return isShallowWaterByLength(y) || isShallowWaterByWidth(x);
    }

    private boolean isShallowWaterByLength(int y) {
        return lessOrEqualsZero(y) || MathUtil.moreThan(y, getIslandLength());
    }

    private boolean isShallowWaterByWidth(int x) {
        return lessOrEqualsZero(x) || MathUtil.moreThan(x, getIslandWidth());
    }

    @Override
    public int getIslandLength() {
        return gameField.ISLAND_LENGTH;
    }

    @Override
    public int getIslandWidth() {
        return gameField.ISLAND_WIDTH;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<GameObjectName, Set<BasicItem>> getCurrentAreaMap(int x, int y) {
        return (Map<GameObjectName, Set<BasicItem>>) islandMap[y][x];
    }

    @Override
    public Map<GameObjectName, Set<BasicItem>> getCurrentAreaMap(Coordinates coordinates) {
        return getCurrentAreaMap(coordinates.getX(), coordinates.getY());
    }

    @Override
    public Set<BasicItem> getCurrentItemArea(Coordinates coordinates, GameObjectName objectName) {
        return getCurrentAreaMap(coordinates).get(objectName);
    }

    @Override
    public boolean isEnoughPlaceOnArea(Coordinates coordinates, GameObjectName objectName) {
        int itemsOnArea = GAME_FIELD_MANAGER.getCurrentItemArea(coordinates, objectName).size();

        return MathUtil.lessThan(itemsOnArea, maxCountOnArea(objectName));
    }

    @Override
    public boolean isEnoughPlaceOnArea(int itemsCount, GameObjectName objectName) {
        return MathUtil.lessThan(itemsCount, maxCountOnArea(objectName));
    }

    private int maxCountOnArea(GameObjectName objectName) {
        return GAME_SETTINGS.getMaxCountOnArea(objectName);
    }

    @Override
    public void serialize() {
        SerializeUtil.serialize(GAME_FIELD_SERIALIZE, gameField);
    }

    @Override
    public void deserialize() {
        gameField = (GameField) SerializeUtil.deserialize(GAME_FIELD_SERIALIZE);
        islandMap = gameField.islandMap;
    }

    private class GameFieldInit {
        void createAndFill() {
            createIslandMap();
            islandMap = gameField.islandMap;
            initIslandMap();
            addInitGameObjects();
        }

        void createIslandMap() {
            int shallowWater = 2;
            int height = gameField.ISLAND_LENGTH + shallowWater;
            int width = gameField.ISLAND_WIDTH + shallowWater;

            gameField.islandMap = new Object[height][width];
        }

        void initIslandMap() {
            for (y = 0; y < islandMap.length; y++) {
                for (x = 0; x < islandMap[y].length; x++) {
                    islandMap[y][x] = (isShallowWater(x, y)) ? true : getNewBasicAreaMap();
                }
            }
        }

        void addInitGameObjects() {
            for (y = 0; y < islandMap.length; y++) {
                if (isShallowWaterByLength(y)) continue;
                for (x = 0; x < islandMap[y].length; x++) {
                    if (isShallowWaterByWidth(x)) continue;
                    var areaMap = getCurrentAreaMap(x, y);
                    areaMap
                            .forEach((key, value) -> fillInitArea(key, value, x, y));
                }
            }
        }

        void fillInitArea(GameObjectName objectName, Set<? extends BasicItem> area, int x, int y) {
            int itemsAmount = calculateNewItemsAmount(objectName);
            int itemsAbsence = 0;

            while (MathUtil.moreThan(itemsAmount, itemsAbsence)) {
                area.add(ITEM_FACTORY.createIslandItem(objectName, x, y));
                itemsAmount--;
            }
        }

        int calculateNewItemsAmount(GameObjectName objectName) {
            int halfMaxCountOnArea = GAME_SETTINGS.getMaxCountOnArea(objectName);
            return IslandRandom.getRandomNumber(halfMaxCountOnArea);
        }
    }
}
