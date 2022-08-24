package model.abstraction;

import lombok.Getter;
import lombok.Setter;
import resources.KeysProperties;

public abstract class BasicItem {
    @Getter
    @Setter
    private int x;

    @Getter
    @Setter
    private int y;

    @Getter
    protected KeysProperties classKey;

    public BasicItem(int y, int x) {
        this.y = y;
        this.x = x;
    }

}
