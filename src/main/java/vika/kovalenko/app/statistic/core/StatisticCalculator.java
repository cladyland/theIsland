package vika.kovalenko.app.statistic.core;

import vika.kovalenko.app.core.constants.GameObjectName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static vika.kovalenko.app.core.manage.imp.Manager.GAME_FIELD_MANAGER;

public class StatisticCalculator {
    private Map<GameObjectName, Integer> itemsCount;

    public Map<GameObjectName, Integer> calculateAllItemsOnIsland() {
        itemsCount = new HashMap<>();
        int initCount = 0;

        Arrays.stream(GameObjectName.values())
                .forEach(name -> itemsCount.put(name, initCount));

        return fillTotalCountMap();
    }

    private Map<GameObjectName, Integer> fillTotalCountMap() {
        for (int y = 0; y < GAME_FIELD_MANAGER.getIslandLength(); y++) {
            for (int x = 0; x < GAME_FIELD_MANAGER.getIslandWidth(); x++) {
                if (GAME_FIELD_MANAGER.isShallowWater(x, y)) {
                    continue;
                }
                var map = GAME_FIELD_MANAGER.getCurrentAreaMap(x, y);
                map.forEach((key, value) -> putOnMap(key, value.size()));
            }
        }
        return itemsCount;
    }

    private void putOnMap(GameObjectName key, int value) {
        int oldValue = itemsCount.get(key);
        itemsCount.put(key, oldValue + value);
    }
}
