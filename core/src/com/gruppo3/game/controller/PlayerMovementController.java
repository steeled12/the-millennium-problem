package com.gruppo3.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gruppo3.game.model.Player;

public class PlayerMovementController {
    public Player player = Player.getPlayer();
    float playerSpeed = 300f;

    public void updateInput(){
        moveLeft();
        moveRight();
        moveDown();
        moveUp();
        checkBorder();
    }

    private void checkBorder(){
        // make sure the player stays within the screen bounds
        if (player.getPlayerBox().x < 0)
            player.getPlayerBox().x = 0;

        if (player.getPlayerBox().x > 1920 - 64)
            player.getPlayerBox().x = 1920 - 64;

        if (player.getPlayerBox().y < 0)
            player.getPlayerBox().y = 0;

        if (player.getPlayerBox().y > 1080)
            player.getPlayerBox().y = 1080;
    }

    private void moveLeft(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            player.getPlayerBox().x -= playerSpeed * Gdx.graphics.getDeltaTime();
    }

    private void moveRight(){
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            player.getPlayerBox().x += playerSpeed * Gdx.graphics.getDeltaTime();
    }

    private void moveUp(){
        if (Gdx.input.isKeyPressed((Input.Keys.UP)))
            player.getPlayerBox().y += playerSpeed * Gdx.graphics.getDeltaTime();
    }

    private void moveDown(){
        if (Gdx.input.isKeyPressed((Input.Keys.DOWN))) {
            player.getPlayerBox().y -= playerSpeed * Gdx.graphics.getDeltaTime();
        }
    }
}
