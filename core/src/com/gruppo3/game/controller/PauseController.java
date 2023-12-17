package com.gruppo3.game.controller;

import com.badlogic.gdx.Input.Keys;
import com.gruppo3.game.MyGame;
import com.gruppo3.game.MyGame.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class PauseController extends InputAdapter {

    MyGame game;

    public PauseController(MyGame game) {
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.ESCAPE) {
            game.gameState = game.gameState.equals(GameState.RUNNING) ? GameState.PAUSED : GameState.RUNNING;
            Gdx.app.log("PauseController", game.gameState.name());
        }
        return false;
    }
}