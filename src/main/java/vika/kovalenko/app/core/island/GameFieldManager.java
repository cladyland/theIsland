package vika.kovalenko.app.core.island;

import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.items.BasicItem;
import vika.kovalenko.app.core.manage.handler.SerializableHandler;
import vika.kovalenko.app.core.utility.Coordinates;

import java.util.Map;
import java.util.Set;

public interface GameFieldManager extends SerializableHandler {
    void createAndFillNewIsland(int height, int width);

    boolean isShallowWater(int x, int y);

    int getIslandLength();

    int getIslandWidth();

    Map<GameObjectName, Set<BasicItem>> getCurrentAreaMap(int x, int y);

    Map<GameObjectName, Set<BasicItem>> getCurrentAreaMap(Coordinates coordinates);

    Set<BasicItem> getCurrentItemArea(Coordinates coordinates, GameObjectName objectName);

    boolean isEnoughPlaceOnArea(Coordinates coordinates, GameObjectName objectName);

    boolean isEnoughPlaceOnArea(int itemsCount, GameObjectName objectName);
}
