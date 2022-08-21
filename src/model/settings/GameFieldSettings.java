package model.settings;

import resources.KeysProperties;
import service.FindAppProperties;

import static resources.KeysProperties.FIELD;
import static resources.KeysProperties.GAME;

public class GameFieldSettings {
    private static int HEIGHT;
    private static int WIDTH;

    private GameFieldSettings(){
        HEIGHT = Integer.parseInt(FindAppProperties.getAppProperty(GAME, FIELD, KeysProperties.HEIGHT));
        WIDTH = Integer.parseInt(FindAppProperties.getAppProperty(GAME, FIELD, KeysProperties.WIDTH));
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getWidth() {
        return WIDTH;
    }

}
