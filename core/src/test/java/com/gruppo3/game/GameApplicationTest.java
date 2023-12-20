package com.gruppo3.game;


import com.badlogic.gdx.Input;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.PauseController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameApplicationTest{
    @Test
    public void testStartNewGame() {
        MyGame game = new MyGame();
        game.create();
        Assertions.assertEquals(game.gameState, MyGame.GameState.RUNNING);
    }

    @Test
    public void testPauseGame() {
        MyGame game = new MyGame();
        game.create();
        PauseController pauseController = new PauseController(game);
        pauseController.keyDown(Input.Keys.ESCAPE);
        Assertions.assertEquals(game.gameState, MyGame.GameState.PAUSED);
    }

    @Test
    public void testResumeGame() {
        MyGame game = new MyGame();
        game.create();
        game.gameState = MyGame.GameState.PAUSED;
        PauseController pauseController = new PauseController(game);
        pauseController.keyDown(Input.Keys.ESCAPE);
        Assertions.assertEquals(game.gameState, MyGame.GameState.RUNNING);
    }

}
