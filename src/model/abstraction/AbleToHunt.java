package model.abstraction;

import java.util.Random;

public interface AbleToHunt {
    default boolean hunting(int eatingProbability) {
        Random random = new Random();
        int maximumProbability = 100;
        boolean huntingSuccess = false;
        int attackChance = random.nextInt(maximumProbability);
        if (attackChance <= eatingProbability) {
            huntingSuccess = true;
        }
        return huntingSuccess;
    }
}
