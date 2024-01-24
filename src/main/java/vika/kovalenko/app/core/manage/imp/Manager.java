package vika.kovalenko.app.core.manage.imp;

import vika.kovalenko.app.core.manage.AnimalManager;
import vika.kovalenko.app.core.manage.EatingControl;
import vika.kovalenko.app.core.island.GameFieldManager;
import vika.kovalenko.app.core.manage.LifeCycleControl;
import vika.kovalenko.app.core.manage.MovementControl;
import vika.kovalenko.app.core.manage.ReproducingControl;
import vika.kovalenko.app.core.island.GameFieldManagerImp;
import vika.kovalenko.app.core.utility.GameSettings;
import vika.kovalenko.app.core.utility.IslandItemFactory;
import vika.kovalenko.app.primary.manage.GameManager;
import vika.kovalenko.app.primary.game.GameManagerImp;
import vika.kovalenko.app.statistic.StatisticManager;
import vika.kovalenko.app.statistic.core.StatisticManagerImp;

public final class Manager {
    public static final GameSettings GAME_SETTINGS = GameSettings.getINSTANCE();
    public static final GameFieldManager GAME_FIELD_MANAGER = new GameFieldManagerImp();
    public static final IslandItemFactory ITEM_FACTORY = new IslandItemFactory();
    public static final AnimalManager ANIMAL_MANAGER = new AnimalManagerImp();
    public static final MovementControl MOVEMENT_CONTROL = new MovementControlImp();
    public static final EatingControl EATING_CONTROL = new EatingControlImp();
    public static final ReproducingControl REPRODUCING_CONTROL = new ReproducingControlImp();
    public static final StatisticManager STATISTIC_MANAGER = new StatisticManagerImp();
    public static LifeCycleControl LIFE_CYCLE_CONTROL = new LifeCycleControlImp();
    public static final GameManager GAME_MANAGER = new GameManagerImp();
}
