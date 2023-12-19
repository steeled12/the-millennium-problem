package com.gruppo3.test;


import com.badlogic.gdx.Input;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.PauseController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameApplicationTest{
    @Test
    public void testStartNewGame() {
        MyGame game = new MyGame();
        game.create();
        assertEquals(game.gameState, MyGame.GameState.RUNNING);
    }

    @Test
    public void testPauseGame() {
        MyGame game = new MyGame();
        game.create();
        PauseController pauseController = new PauseController(game);
        pauseController.keyDown(Input.Keys.ESCAPE);
        assertEquals(game.gameState, MyGame.GameState.PAUSED);
    }

    @Test
    public void testResumeGame() {
        MyGame game = new MyGame();
        game.create();
        game.gameState = MyGame.GameState.PAUSED;
        PauseController pauseController = new PauseController(game);
        pauseController.keyDown(Input.Keys.ESCAPE);
        assertEquals(game.gameState, MyGame.GameState.RUNNING);
    }

    @Test
    public void testSaveLoadGame() {
        MyGame game = new MyGame();
        game.create();

    }
}
