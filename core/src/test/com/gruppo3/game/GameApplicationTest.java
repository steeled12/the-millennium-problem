package com.gruppo3.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.gruppo3.game.controller.PauseController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.backends.headless.HeadlessApplication;

public class GameApplicationTest {

    MyGame game;
    HeadlessApplication headlessApplication;

    @BeforeEach
    public void setUp(){
        game = new MyGame();
        if (headlessApplication == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            headlessApplication = new HeadlessApplication(game, config);
        }
    }
    @Test
    public void testStartNewGame() {

        Assertions.assertEquals(game.gameState, MyGame.GameState.RUNNING);
    }

    @Test
    public void testPauseGame() {

        PauseController pauseController = new PauseController(game);
        pauseController.keyDown(Input.Keys.ESCAPE);
        Assertions.assertEquals(game.gameState, MyGame.GameState.PAUSED);
    }

    @Test
    public void testResumeGame() {

        game.gameState = MyGame.GameState.PAUSED;
        PauseController pauseController = new PauseController(game);
        pauseController.keyDown(Input.Keys.ESCAPE);
        Assertions.assertEquals(game.gameState, MyGame.GameState.RUNNING);
    }

}
