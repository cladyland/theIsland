package vika.kovalenko.app.core.items.plants;

import vika.kovalenko.app.core.items.Plant;

import static vika.kovalenko.app.core.constants.GameObjectName.HERB;

public class Herb extends Plant {
    {
        classKey = HERB;
        initWeight();
    }

    @Override
    protected void initWeight() {
        super.initWeight();
        inedibleWeight = 0;
    }

    @Override
    public void regenerate() {
        isAlive = true;
        initWeight();
    }
}
