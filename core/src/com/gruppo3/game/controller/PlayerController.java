package com.gruppo3.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.TestScreen;
import com.badlogic.gdx.InputAdapter;

public class PlayerController extends InputAdapter {
    private Player player;
    float playerSpeed;
    Animation<TextureRegion> animation;

    public PlayerController() {
        this.player = Player.getPlayer();
        this.playerSpeed = 150f;
    }

    public Animation<TextureRegion> getAnimationToRender() {
        return animation;
    }

    public void updateInput() {
        TestScreen.camera.position.set(player.getPlayerBox().x, player.getPlayerBox().y,
                TestScreen.camera.position.z);

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

    private boolean isColliding() {
        MapLayer collisionObjectLayer = TestScreen.map.getLayers().get("Collisione");
        MapObjects objects = collisionObjectLayer.getObjects();

        for (RectangleMapObject rectangleMapObject : objects.getByType(RectangleMapObject.class)) {
            if (player.getPlayerBox().overlaps(rectangleMapObject.getRectangle())) {
                return true;
            }
        }

        return false;
    }

    private int moveLeft() {
        float previousX = player.getPlayerBox().x;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.getPlayerBox().x -= playerSpeed * Gdx.graphics.getDeltaTime();
            player.setPlayerDirection(Player.PlayerDirection.WEAST);

            if (isColliding()) {
                player.getPlayerBox().x = previousX;
                return 0;
            }
            return 1;
        }
        return 0;
    }

    private int moveRight() {
        float previousX = player.getPlayerBox().x;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.getPlayerBox().x += playerSpeed * Gdx.graphics.getDeltaTime();
            player.setPlayerDirection(Player.PlayerDirection.EAST);

            if (isColliding()) {
                player.getPlayerBox().x = previousX;
                return 0;
            }
            return 1;
        }
        return 0;
    }

    private int moveUp() {
        float previousY = player.getPlayerBox().y;
        if (Gdx.input.isKeyPressed((Input.Keys.UP))) {
            player.getPlayerBox().y += playerSpeed * Gdx.graphics.getDeltaTime();
            player.setPlayerDirection(Player.PlayerDirection.NORTH);

            if (isColliding()) {
                player.getPlayerBox().y = previousY;
                return 0;
            }
            return 1;
        }
        return 0;
    }

    private int moveDown() {
        float previousY = player.getPlayerBox().y;
        if (Gdx.input.isKeyPressed((Input.Keys.DOWN))) {
            player.getPlayerBox().y -= playerSpeed * Gdx.graphics.getDeltaTime();
            player.setPlayerDirection(Player.PlayerDirection.SOUTH);

            if (isColliding()) {
                player.getPlayerBox().y = previousY;
                return 0;
            }
            return 1;
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

        if (keycode == Input.Keys.NUM_1)
            SaveController.loadSave(0);
        if (keycode == Input.Keys.NUM_2)
            SaveController.loadSave(1);
        if (keycode == Input.Keys.NUM_3)
            SaveController.loadSave(2);
        if (keycode == Input.Keys.NUM_4)
            SaveController.deleteSave();

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
