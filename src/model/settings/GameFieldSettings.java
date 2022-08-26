package model.settings;

import resources.KeysProperties;
import service.FindAppProperties;

import static resources.KeysProperties.FIELD;
import static resources.KeysProperties.GAME;

public class GameFieldSettings {
    private final int HEIGHT;
    private final int WIDTH;

    GameFieldSettings() {
        HEIGHT = getProperty(GAME, FIELD, KeysProperties.HEIGHT);
        WIDTH = getProperty(GAME, FIELD, KeysProperties.WIDTH);
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    private int getProperty(KeysProperties... key) {
        return Integer.parseInt(FindAppProperties.getInstance().getAppProperty(key));
    }
}
