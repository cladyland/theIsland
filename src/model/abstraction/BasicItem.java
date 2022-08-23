package model.abstraction;

import lombok.Getter;
import lombok.Setter;

public abstract class BasicItem {
    @Getter
    @Setter
    private int x;

    @Getter
    @Setter
    private int y;

    public BasicItem(int y, int x) {
        this.y = y;
        this.x = x;
    }

}
