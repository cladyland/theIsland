package statistic;

import model.abstraction.Animal;
import model.herb.Herb;
import model.settings.GameField;
import resources.GameObjectName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static statistic.StatisticName.INITIAL_POPULATION;

public class IslandStatistic {
    private GameField gameField;
    private HashMap<GameObjectName, HashMap<StatisticName, AtomicInteger>> islandObjectsStatisticCache;
    private HashMap<StatisticName, AtomicInteger> statistics;
    private HashMap<GameObjectName, ArrayList<Animal>> animalsMapToStatistic;
    private ArrayList<Herb> plantsListToStatistic;

    AtomicInteger initialPopulation;
    AtomicInteger finitePopulation;
    AtomicInteger initialNumberOfPlants;
    AtomicInteger finiteNumberOfPlants;
    AtomicInteger currentNumberOfBornAnimals;
    AtomicInteger currentNumberOfDeadAnimals;
    AtomicInteger populationNumberChange;
    AtomicInteger plantsNumberChange;

    {
        StatisticName[] statisticNames = StatisticName.values();
        statistics = new HashMap<>();
        for (StatisticName statisticName : statisticNames) {
            statistics.put(statisticName, new AtomicInteger());
        }
    }

    public IslandStatistic(GameField gameField) {
        this.gameField = gameField;
        islandObjectsStatisticCache = new HashMap<>();
        animalsMapToStatistic = new HashMap<>();
        plantsListToStatistic = new ArrayList<>();
        initializeCacheMap();
    }

    public void initialPopulation(Map<GameObjectName, ArrayList<?>> areaMap) {
        areaMap
                .entrySet()
                .stream()
                .iterator()
                .forEachRemaining((entry) -> countPopulation(entry.getKey(), entry.getValue().size()));
    }

    public void initialObjects() {
        islandObjectsStatisticCache
                .entrySet()
                .iterator()
                .forEachRemaining(entry -> System.out.println(entry.getKey() + " population " +
                                                              entry.getValue().get(INITIAL_POPULATION)));
    }

    private void countPopulation(GameObjectName objectName, int size) {
        islandObjectsStatisticCache
                .get(objectName)
                .get(INITIAL_POPULATION)
                .addAndGet(size);
    }

    public void collectObjectsForStatistics() {
        for (int y = 0; y < gameField.getISLAND_HEIGHT(); y++) {
            for (int x = 0; x < gameField.getISLAND_WIDTH(); x++) {
                boolean isShallowWater = x == 0 || y == 0
                                         || x > gameField.getISLAND_WIDTH()
                                         || y > gameField.getISLAND_HEIGHT();
                if (!isShallowWater) {
                    var currentArea = gameField.getCurrentAreaMap(y, x);
                    currentArea
                            .entrySet()
                            .stream()
                            .iterator()
                            .forEachRemaining(entry -> addObjectsForAnalysis(entry.getKey(), entry.getValue()));
                }
            }
        }
    }

    public void printStatistic() {
        animalsMapToStatistic.entrySet().forEach(System.out::println);
        plantsListToStatistic.forEach(System.out::println);
        System.out.println(animalsMapToStatistic.size() + " animals all");
        System.out.println(plantsListToStatistic.size() + " plants all");

        for (var animal : animalsMapToStatistic.entrySet()) {
            System.out.println(animal.getKey() + " " + animal.getValue().size());
        }
    }

    private void addObjectsForAnalysis(GameObjectName objectName, List<?> currentArea) {
        if (objectName.equals(GameObjectName.HERB)) {
            plantsListToStatistic.addAll((ArrayList<Herb>) currentArea);
        } else {
            animalsMapToStatistic.put(objectName, (ArrayList<Animal>) currentArea);
        }
    }

    private void initializeCacheMap() {
        GameObjectName[] objectNames = GameObjectName.values();
        for (GameObjectName objectName : objectNames) {
            islandObjectsStatisticCache.put(objectName, statistics);
        }
    }
}
