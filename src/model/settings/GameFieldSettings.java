package model.settings;

import resources.KeysProperties;
import service.FindAppProperties;

import static resources.KeysProperties.FIELD;
import static resources.KeysProperties.GAME;

public class GameFieldSettings {
    private final int HEIGHT;
    private final int WIDTH;

    GameFieldSettings() {
        HEIGHT = Integer.parseInt(FindAppProperties.getAppProperty(GAME, FIELD, KeysProperties.HEIGHT));
        WIDTH = Integer.parseInt(FindAppProperties.getAppProperty(GAME, FIELD, KeysProperties.WIDTH));
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

}
