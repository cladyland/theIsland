package vika.kovalenko.app.primary.manage;

public interface GameManager {
    void initGame();

    void loadGame();

    void startNewDay();

    void endGame(boolean saveProgress);
}
