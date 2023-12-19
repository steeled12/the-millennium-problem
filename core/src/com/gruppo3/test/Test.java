package com.gruppo3.test;

import com.gruppo3.game.MyGame;
import com.gruppo3.game.MyGame.GameState;
import com.gruppo3.game.controller.PauseController;
import com.badlogic.gdx.Input.Keys;
import org.junit.Assert;
import org.junit.Test;


public class Test {
    
        @Test
        public void testStartNewGame() {
            MyGame game = new MyGame();
            game.create();
            Assert.assertEquals(game.gameState, GameState.RUNNING);
        }

        @Test
        public void testPauseGame() {
            MyGame game = new MyGame();
            game.create();
            PauseController pauseController = new PauseController(game);
            pauseController.keyDown(Keys.ESCAPE);
            Assert.assertEquals(game.gameState, GameState.PAUSED);
        }

        @Test
        public void testResumeGame() {
            MyGame game = new MyGame();
            game.create();
            game.gameState = GameState.PAUSED;
            PauseController pauseController = new PauseController(game);
            pauseController.keyDown(Keys.ESCAPE);
            Assert.assertEquals(game.gameState, GameState.RUNNING);
        }

        @Test
        public void testSaveLoadGame() {
            MyGame game = new MyGame();
            game.create();
            
        }
}
