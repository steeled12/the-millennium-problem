package com.gruppo3.game.model.interactables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.controller.DialogController;

public interface Item {
    Texture getTexture();
    String getName();
    Rectangle getBox();
    void action(DialogController dialogController);
}
