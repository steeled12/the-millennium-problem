package com.gruppo3.game.model.interactables;

import com.badlogic.gdx.Gdx;

public class PickableItem implements Interactable {

    @Override
    public void action() {
        Gdx.app.debug("[Test Interaction]", "Stato porta:!");
    }
}