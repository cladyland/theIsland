package vika.kovalenko.app.core.manage.imp;

import vika.kovalenko.app.core.constants.Action;
import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.items.Animal;
import vika.kovalenko.app.core.manage.AnimalManager;
import vika.kovalenko.app.core.utility.util.IslandRandom;
import vika.kovalenko.app.core.utility.util.MathUtil;

import static vika.kovalenko.app.core.constants.Action.EAT;
import static vika.kovalenko.app.core.manage.imp.Manager.EATING_CONTROL;
import static vika.kovalenko.app.core.manage.imp.Manager.LIFE_CYCLE_CONTROL;
import static vika.kovalenko.app.core.manage.imp.Manager.MOVEMENT_CONTROL;
import static vika.kovalenko.app.core.manage.imp.Manager.REPRODUCING_CONTROL;
import static vika.kovalenko.app.core.utility.util.MathUtil.MAX_PERCENT;

class AnimalManagerImp implements AnimalManager {

    @Override
    synchronized public void chooseAndPerformAction(Animal animal) {
        if (!animal.isAlive()) {
            LIFE_CYCLE_CONTROL.addAnimalToQueue(animal);
            return;
        }

        Action action = chooseAction(animal.getSatietyPercent());

        switch (action) {
            case MOVE -> moveAnimal(animal);
            case EAT -> eating(animal);
            case REPRODUCE -> reproducing(animal);
        }
    }

    private Action chooseAction(int satietyPercent) {
        Action action = IslandRandom.chooseAnimalAction();
        if (shouldEat(satietyPercent)) {
            action = EAT;
        }
        return action;
    }

    private boolean shouldEat(int satietyPercent) {
        int minSatietyForForceEating = 20;
        return MathUtil.lessOrEquals(satietyPercent, minSatietyForForceEating);
    }

    private void moveAnimal(Animal animal) {
        MOVEMENT_CONTROL.chooseDestinationAndMove(animal);
    }

    private void eating(Animal animal) {
        if (MathUtil.lessThan(animal.getSatietyPercent(), MAX_PERCENT)) {
            EATING_CONTROL.findAndEatFood(animal);
        }
    }

    private void reproducing(Animal animal) {
        REPRODUCING_CONTROL.reproduce(animal);
    }

    @Override
    public boolean isCaterpillar(GameObjectName objectName) {
        return objectName.equals(GameObjectName.CATERPILLAR);
    }
}
