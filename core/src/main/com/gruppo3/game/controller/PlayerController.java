package com.gruppo3.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.GameScreen;
import com.badlogic.gdx.InputAdapter;
import com.gruppo3.game.model.Player.PlayerDirection;

public class PlayerController extends InputAdapter {
    private Player player;
    float playerSpeed;
    Animation<TextureRegion> animation;

    public PlayerController() {
        this.player = Player.getPlayer();
        this.playerSpeed = 8f;
        this.animation = player.getIdleAnimation(player.getPlayerDirection());
    }

    public Animation<TextureRegion> getAnimationToRender() {
        return animation;
    }

    public void updateInput() {
        /*
         * GameScreen.camera.position.set(player.getPlayerBox().x,
         * player.getPlayerBox().y,
         * GameScreen.camera.position.z);
         */

        if (moveUp() + moveDown() + moveLeft() + moveRight() > 0) {
            this.animation = player.getWalkAnimation(player.getPlayerDirection());
        } else {
            this.animation = player.getIdleAnimation(player.getPlayerDirection());
        }
    }

    // private void checkBorder() {
    // // make sure the player stays within the screen bounds
    // if (player.getPlayerBox().x < 0)
    // player.getPlayerBox().x = 0;

    // if (player.getPlayerBox().x > 1920 - 64)
    // player.getPlayerBox().x = 1920 - 64;

    // if (player.getPlayerBox().y < 0)
    // player.getPlayerBox().y = 0;

    // if (player.getPlayerBox().y > 1080 - 64)
    // player.getPlayerBox().y = 1080 - 64;
    // }

    // Overload per controllare se il player collide con un oggetto in una specifica
    // direzione
    private boolean isColliding(PlayerDirection direction) {
        MapLayer collisionObjectLayer = GameScreen.levelController.getMap().getLayers().get("Collisioni");
        MapObjects objects = collisionObjectLayer.getObjects();

        for (RectangleMapObject rectangleMapObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle playerRectangle = new Rectangle(player.getPlayerBox());
            Rectangle objectRectangle = rectangleMapObject.getRectangle();

            switch (direction) {
                case WEST:
                    playerRectangle.x -= playerSpeed * Gdx.graphics.getDeltaTime();
                    break;
                case EAST:
                    playerRectangle.x += playerSpeed * Gdx.graphics.getDeltaTime();
                    break;
                case NORTH:
                    playerRectangle.y += playerSpeed * Gdx.graphics.getDeltaTime();
                    break;
                case SOUTH:
                    playerRectangle.y -= playerSpeed * Gdx.graphics.getDeltaTime();
                    break;
            }

            if (playerRectangle.overlaps(objectRectangle)) {
                return true;
            }
        }

        return false;
    }

    private int moveLeft() {
        if (!isColliding(PlayerDirection.WEST)) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.getPlayerBox().x -= playerSpeed * Gdx.graphics.getDeltaTime();
                player.setPlayerDirection(Player.PlayerDirection.WEST);

                return 1;
            }
        }
        return 0;
    }

    private int moveRight() {
        if (!isColliding(PlayerDirection.EAST)) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.getPlayerBox().x += playerSpeed * Gdx.graphics.getDeltaTime();
                player.setPlayerDirection(Player.PlayerDirection.EAST);

                return 1;
            }
        }
        return 0;
    }

    private int moveUp() {
        if (!isColliding(PlayerDirection.NORTH)) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                player.getPlayerBox().y += playerSpeed * Gdx.graphics.getDeltaTime();
                player.setPlayerDirection(Player.PlayerDirection.NORTH);

                return 1;
            }
        }
        return 0;
    }

    private int moveDown() {
        if (!isColliding(PlayerDirection.SOUTH)) {
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                player.getPlayerBox().y -= playerSpeed * Gdx.graphics.getDeltaTime();
                player.setPlayerDirection(Player.PlayerDirection.SOUTH);

                return 1;
            }
        }
        return 0;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SHIFT_LEFT) {
            playerSpeed = 16f;
        }
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            moveLeft();
        } else if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            moveRight();
        } else if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            moveUp();
        } else if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            moveDown();
        }
        Gdx.app.log("PlayerController", "Player position: " + player.getPlayerBox().x + " " + player.getPlayerBox().y);
        /*
         * if (keycode == Input.Keys.NUM_1)
         * SaveController.loadSave(0);
         * if (keycode == Input.Keys.NUM_2)
         * SaveController.loadSave(1);
         * if (keycode == Input.Keys.NUM_3)
         * SaveController.loadSave(2);
         * if (keycode == Input.Keys.NUM_4)
         * SaveController.deleteSave();
         * 
         * if (keycode == Input.Keys.NUM_5) {
         * SaveController.save();
         * }
         */
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SHIFT_LEFT) {
            playerSpeed = 8f;
        }
        return true;
    }
}
