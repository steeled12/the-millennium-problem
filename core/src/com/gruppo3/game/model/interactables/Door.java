package com.gruppo3.game.model.interactables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Door implements Interactable {

    boolean isOpen;
    Texture openImage;
    Texture closedImage;
    Rectangle doorBox;

    public void dispose() {
        openImage.dispose();
        closedImage.dispose();
    }

    @Override
    public void action() {
        if (!isOpen) {
            isOpen = true;
        } else {
            isOpen = false;
        }

        Gdx.app.debug("[Test Interaction]", "Stato porta: " + isOpen + "!");
    }
}