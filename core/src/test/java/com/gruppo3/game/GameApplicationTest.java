package com.gruppo3.game;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.PauseController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.backends.headless.HeadlessApplication;

public class GameApplicationTest{

    HeadlessApplication headlessApplication;
    @Test
    public void testStartNewGame() {
        MyGame game = new MyGame();
        if (headlessApplication == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            headlessApplication = new HeadlessApplication(game, config);
        }

        Assertions.assertEquals(game.gameState, MyGame.GameState.RUNNING);
    }

    @Test
    public void testPauseGame() {
        MyGame game = new MyGame();
        if (headlessApplication == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            headlessApplication = new HeadlessApplication(game, config);
        }
        PauseController pauseController = new PauseController(game);
        pauseController.keyDown(Input.Keys.ESCAPE);
        Assertions.assertEquals(game.gameState, MyGame.GameState.PAUSED);
    }

    @Test
    public void testResumeGame() {
        MyGame game = new MyGame();
        if (headlessApplication == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            headlessApplication = new HeadlessApplication(game, config);
        }
        game.gameState = MyGame.GameState.PAUSED;
        PauseController pauseController = new PauseController(game);
        pauseController.keyDown(Input.Keys.ESCAPE);
        Assertions.assertEquals(game.gameState, MyGame.GameState.RUNNING);
    }

}
