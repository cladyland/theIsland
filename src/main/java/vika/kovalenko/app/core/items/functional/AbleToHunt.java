package vika.kovalenko.app.core.items.functional;

import vika.kovalenko.app.core.utility.util.IslandRandom;
import vika.kovalenko.app.core.utility.util.MathUtil;

public interface AbleToHunt {

    default boolean hunt(int eatingProbability) {
        int maximumProbability = 100;
        boolean huntingSuccess = false;

        int attackChance = IslandRandom.getRandomNumber(maximumProbability);

        if (MathUtil.lessOrEquals(attackChance, eatingProbability)) {
            huntingSuccess = true;
        }
        return huntingSuccess;
    }
}
