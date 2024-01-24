package vika.kovalenko.app.core.utility;

import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.items.Animal;
import vika.kovalenko.app.core.items.BasicItem;
import vika.kovalenko.app.core.items.animals.herbivorous.*;
import vika.kovalenko.app.core.items.animals.predators.*;
import vika.kovalenko.app.core.items.plants.Herb;

@SuppressWarnings("unchecked")
public class IslandItemFactory {

    public <T extends BasicItem> T createIslandItem(GameObjectName gameObjectName, int x, int y) {
        T item = defineAndCreate(gameObjectName);
        item.setCoordinates(x, y);

        return item;
    }

    public <T extends Animal> T createYoungAnimal(Animal mother) {
        var coordinates = mother.getCoordinates();
        Animal offspring = createIslandItem(mother.getClassKey(), coordinates.getX(), coordinates.getY());
        offspring.setYoung(true);

        return (T) offspring;
    }

    private <T extends BasicItem> T defineAndCreate(GameObjectName gameObjectName) {
        return switch (gameObjectName) {
            case WOLF -> (T) new Wolf();
            case BOA -> (T) new Boa();
            case FOX -> (T) new Fox();
            case BEAR -> (T) new Bear();
            case EAGLE -> (T) new Eagle();
            case HORSE -> (T) new Horse();
            case DEER -> (T) new Deer();
            case RABBIT -> (T) new Rabbit();
            case MOUSE -> (T) new Mouse();
            case GOAT -> (T) new Goat();
            case SHEEP -> (T) new Sheep();
            case BOAR -> (T) new Boar();
            case BUFFALO -> (T) new Buffalo();
            case DUCK -> (T) new Duck();
            case CATERPILLAR -> (T) new Caterpillar();
            case HERB -> (T) new Herb();
        };
    }
}
