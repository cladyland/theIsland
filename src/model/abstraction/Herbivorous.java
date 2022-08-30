package model.abstraction;

public abstract class Herbivorous extends Animal {
    public Herbivorous(int y, int x, boolean isYoung) {
        super(y, x, isYoung);
        isPredator = false;
    }
}
