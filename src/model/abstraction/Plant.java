package model.abstraction;

public class Plant extends BasicItem implements Regenerative {
    public boolean isEaten;

    public Plant(int y, int x) {
        super(y, x);
        isEaten = false;
    }

    @Override
    public void regenerate() {

    }
}
