package model.settings;

import resources.KeysProperties;
import service.AppProperties;

import static resources.KeysProperties.FIELD;
import static resources.KeysProperties.GAME;

public class GameFieldSettings {
    private final int HEIGHT;
    private final int WIDTH;

    GameFieldSettings() {
        HEIGHT = getGameFieldProperty(KeysProperties.HEIGHT);
        WIDTH = getGameFieldProperty(KeysProperties.WIDTH);
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    private int getGameFieldProperty(KeysProperties key) {
        return Integer.parseInt(AppProperties.getInstance().getAppProperty(GAME, FIELD, key));
    }
}
