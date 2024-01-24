package vika.kovalenko.app.core.items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vika.kovalenko.app.core.constants.GameObjectName;
import vika.kovalenko.app.core.utility.util.MathUtil;
import vika.kovalenko.app.core.utility.Coordinates;

import java.io.Serial;
import java.io.Serializable;

import static vika.kovalenko.app.core.constants.KeyProperty.WEIGHT;
import static vika.kovalenko.app.core.manage.imp.Manager.GAME_SETTINGS;

@NoArgsConstructor
public abstract class BasicItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Getter
    protected GameObjectName classKey;
    @Getter
    @Setter
    protected boolean isAlive;
    protected double weight;
    protected double inedibleWeight;
    @Getter
    protected Coordinates coordinates;

    {
        coordinates = new Coordinates();
        isAlive = true;
    }

    protected void initWeight() {
        String itemWeight = GAME_SETTINGS.getAppProperty(classKey, WEIGHT);
        weight = MathUtil.toDouble(itemWeight);
    }

    public double getEdibleWeight() {
        return weight - inedibleWeight;
    }

    public boolean onlyInediblePartRemained() {
        return MathUtil.lessOrEquals(weight, inedibleWeight);
    }

    public void reduceWeight(double amount) {
        weight -= amount;
    }

    public void setCoordinates(int x, int y) {
        coordinates.setX(x);
        coordinates.setY(y);
    }
}
