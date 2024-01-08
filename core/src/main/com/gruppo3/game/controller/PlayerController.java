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
    }

    public Animation<TextureRegion> getAnimationToRender() {
        return animation;
    }

    public void updateInput() {
        GameScreen.camera.position.set(player.getPlayerBox().x, player.getPlayerBox().y,
                GameScreen.camera.position.z);

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
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.getPlayerBox().x -= playerSpeed * Gdx.graphics.getDeltaTime();
                player.setPlayerDirection(Player.PlayerDirection.WEST);

                return 1;
            }
        }
        return 0;
    }

    private int moveRight() {
        if (!isColliding(PlayerDirection.EAST)) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.getPlayerBox().x += playerSpeed * Gdx.graphics.getDeltaTime();
                player.setPlayerDirection(Player.PlayerDirection.EAST);

                return 1;
            }
        }
        return 0;
    }

    private int moveUp() {
        if (!isColliding(PlayerDirection.NORTH)) {
            if (Gdx.input.isKeyPressed((Input.Keys.UP))) {
                player.getPlayerBox().y += playerSpeed * Gdx.graphics.getDeltaTime();
                player.setPlayerDirection(Player.PlayerDirection.NORTH);

                return 1;
            }
        }
        return 0;
    }

    private int moveDown() {
        if (!isColliding(PlayerDirection.SOUTH)) {
            if (Gdx.input.isKeyPressed((Input.Keys.DOWN))) {
                player.getPlayerBox().y -= playerSpeed * Gdx.graphics.getDeltaTime();
                player.setPlayerDirection(Player.PlayerDirection.SOUTH);

                return 1;
            }
        }
        return 0;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            moveLeft();
        } else if (keycode == Input.Keys.RIGHT) {
            moveRight();
        } else if (keycode == Input.Keys.UP) {
            moveUp();
        } else if (keycode == Input.Keys.DOWN) {
            moveDown();
        }
        Gdx.app.log("PlayerController", "Player position: " + player.getPlayerBox().x + " " + player.getPlayerBox().y);
        /* if (keycode == Input.Keys.NUM_1)
            SaveController.loadSave(0);
        if (keycode == Input.Keys.NUM_2)
            SaveController.loadSave(1);
        if (keycode == Input.Keys.NUM_3)
            SaveController.loadSave(2);
        if (keycode == Input.Keys.NUM_4)
            SaveController.deleteSave(); */

        if (keycode == Input.Keys.NUM_5) {
            SaveController.save();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    // public void Interaction() {
    // if (Gdx.input.isKeyPressed(Input.Keys.I)) {
    // float playerX = player.getPlayerBox().x;
    // float playerY = player.getPlayerBox().y;
    // float objectX = Object.getObject().getObjectBox().x;
    // float objectY = Object.getObject().getObjectBox().y;

    // float distance = (float ) Math.sqrt(Math.pow(objectX - playerX, 2) +
    // Math.pow(objectY - playerY, 2));

    // if (distance < 5 && Object.getObject().isInteractable()) {

    // Object.getObject().action();

    // }
    // }
    // }
    // }
}
