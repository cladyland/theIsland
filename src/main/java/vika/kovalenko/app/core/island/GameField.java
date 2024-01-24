package vika.kovalenko.app.core.island;

import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.items.BasicItem;
import vika.kovalenko.app.core.items.animals.herbivorous.*;
import vika.kovalenko.app.core.items.animals.predators.*;
import vika.kovalenko.app.core.items.plants.Herb;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static vika.kovalenko.app.core.constants.GameObjectName.*;

class GameField implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected final int ISLAND_LENGTH;
    protected final int ISLAND_WIDTH;
    protected volatile Object[][] islandMap;

    protected GameField(int islandLength, int islandWidth) {
        this.ISLAND_LENGTH = islandLength;
        this.ISLAND_WIDTH = islandWidth;
    }

    protected static class Area {
        private static Map<GameObjectName, Set<? extends BasicItem>> basicArea;

        protected static Map<GameObjectName, Set<? extends BasicItem>> getNewBasicAreaMap() {
            fillBasicAreaMap();
            return new ConcurrentHashMap<>(basicArea);
        }

        private static void fillBasicAreaMap() {
            basicArea = new HashMap<>();
            basicArea.put(WOLF, new HashSet<Wolf>());
            basicArea.put(BOA, new HashSet<Boa>());
            basicArea.put(FOX, new HashSet<Fox>());
            basicArea.put(BEAR, new HashSet<Bear>());
            basicArea.put(EAGLE, new HashSet<Eagle>());
            basicArea.put(HORSE, new HashSet<Horse>());
            basicArea.put(DEER, new HashSet<Deer>());
            basicArea.put(RABBIT, new HashSet<Rabbit>());
            basicArea.put(MOUSE, new HashSet<Mouse>());
            basicArea.put(GOAT, new HashSet<Goat>());
            basicArea.put(SHEEP, new HashSet<Sheep>());
            basicArea.put(BOAR, new HashSet<Boar>());
            basicArea.put(BUFFALO, new HashSet<Buffalo>());
            basicArea.put(DUCK, new HashSet<Duck>());
            basicArea.put(CATERPILLAR, new HashSet<Caterpillar>());
            basicArea.put(HERB, new HashSet<Herb>());
        }
    }
}
