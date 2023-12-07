package com.gruppo3.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.screens.TestScreen;

public class PlayerController {
    public Player player = Player.getPlayer();
    float playerSpeed = 300f;

    public void updateInput() {
        float previousX = player.getPlayerBox().x;
        float previousY = player.getPlayerBox().y;

        moveLeft();
        moveRight();
        moveDown();
        moveUp();
        // checkBorder();
        if (isColliding()) {
            player.getPlayerBox().x = previousX;
            player.getPlayerBox().y = previousY;
        }
        TestScreen.camera.position.set(player.getPlayerBox().x, player.getPlayerBox().y, TestScreen.camera.position.z);
    }

    private void checkBorder() {
        // make sure the player stays within the screen bounds
        if (player.getPlayerBox().x < 0)
            player.getPlayerBox().x = 0;

        if (player.getPlayerBox().x > 1920 - 64)
            player.getPlayerBox().x = 1920 - 64;

        if (player.getPlayerBox().y < 0)
            player.getPlayerBox().y = 0;

        if (player.getPlayerBox().y > 1080 - 64)
            player.getPlayerBox().y = 1080 - 64;
    }

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

    private void moveLeft() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            player.getPlayerBox().x -= playerSpeed * Gdx.graphics.getDeltaTime();
    }

    private void moveRight() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            player.getPlayerBox().x += playerSpeed * Gdx.graphics.getDeltaTime();
    }

    private void moveUp() {
        if (Gdx.input.isKeyPressed((Input.Keys.UP)))
            player.getPlayerBox().y += playerSpeed * Gdx.graphics.getDeltaTime();
    }

    private void moveDown() {
        if (Gdx.input.isKeyPressed((Input.Keys.DOWN))) {
            player.getPlayerBox().y -= playerSpeed * Gdx.graphics.getDeltaTime();
        }
    }

    public void Interaction() {
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
                float playerX = player.getPlayerBox().x;
                float playerY = player.getPlayerBox().y;
                float objectX = Object.getObject().getObjectBox().x;
                float objectY = Object.getObject().getObjectBox().y;

                float distance = (float) Math.sqrt(Math.pow(objectX - playerX, 2) + Math.pow(objectY - playerY, 2));

                if (distance < 5 && Object.getObject().isInteractable()) {

                    Object.getObject().action();

                }
            }
        }
    }
}

