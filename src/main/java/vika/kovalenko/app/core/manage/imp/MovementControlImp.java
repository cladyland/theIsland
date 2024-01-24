package vika.kovalenko.app.core.manage.imp;

import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.constants.MoveDirection;
import vika.kovalenko.app.core.items.Animal;
import vika.kovalenko.app.core.items.BasicItem;
import vika.kovalenko.app.core.manage.MovementControl;
import vika.kovalenko.app.core.utility.Coordinates;
import vika.kovalenko.app.core.utility.util.IslandRandom;
import vika.kovalenko.app.core.utility.util.MathUtil;

import static vika.kovalenko.app.core.constants.KeyProperty.MAX;
import static vika.kovalenko.app.core.constants.KeyProperty.SPEED;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_FIELD_MANAGER;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_SETTINGS;

class MovementControlImp implements MovementControl {

    @Override
    public void chooseDestinationAndMove(Animal animal) {
        Coordinates destination = chooseDestination(animal);
        moveAnimalOnIsland(animal, destination);
    }

    private Coordinates chooseDestination(Animal animal) {
        MoveDirection direction = IslandRandom.chooseDirectionMovement();
        int speed = calculateSpeed(animal.getClassKey());

        return calculateNewCoordinates(animal.getCoordinates(), direction, speed);
    }

    private int calculateSpeed(GameObjectName animalName) {
        String maxSpeed = GAME_SETTINGS.getAppProperty(animalName, MAX, SPEED);
        return IslandRandom.getRandomNumber(MathUtil.toInt(maxSpeed));
    }

    private Coordinates calculateNewCoordinates(Coordinates oldCoordinates, MoveDirection direction, int speed) {
        int x = oldCoordinates.getX();
        int y = oldCoordinates.getY();

        switch (direction) {
            case UP -> y -= speed;
            case DOWN -> y += speed;
            case LEFT -> x -= speed;
            case RIGHT -> x += speed;
        }

        return new Coordinates(x, y);
    }

    private void moveAnimalOnIsland(Animal animal, Coordinates destination) {
        if (canMove(destination, animal.getClassKey())) {
            if (addedToNewArea(animal, destination)) {
                removeFromOldArea(animal, animal.getCoordinates());
                animal.move(destination);
            }
        }
    }

    private boolean canMove(Coordinates newCoordinates, GameObjectName objectName) {
        return !GAME_FIELD_MANAGER.isShallowWater(newCoordinates.getX(), newCoordinates.getY())
               && GAME_FIELD_MANAGER.isEnoughPlaceOnArea(newCoordinates, objectName);
    }

    private boolean addedToNewArea(BasicItem item, Coordinates newCoordinates) {
        return GAME_FIELD_MANAGER.getCurrentItemArea(newCoordinates, item.getClassKey())
                .add(item);
    }

    private void removeFromOldArea(BasicItem item, Coordinates oldCoordinates) {
        GAME_FIELD_MANAGER.getCurrentItemArea(oldCoordinates, item.getClassKey())
                .remove(item);
    }
}
