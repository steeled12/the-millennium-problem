package com.gruppo3.game.model.interactables;

import com.badlogic.gdx.Gdx;

public class Door implements Interactable {

    boolean isOpen;

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