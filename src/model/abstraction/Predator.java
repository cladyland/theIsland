package model.abstraction;

public abstract class Predator extends Animal implements AbleToHunt {

    public Predator(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        isPredator = true;
    }
}
