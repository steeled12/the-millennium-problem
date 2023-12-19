package com.gruppo3.game.model.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gruppo3.game.controller.MenuController;

public abstract class MenuState {

    protected MenuController loader;
    private TextureAtlas atlas;
    protected Skin skin;

    public MenuState(MenuController loader) {
        atlas = new TextureAtlas("flat-earth/skin/flat-earth-ui.atlas");
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"), atlas);
        this.loader = loader;
    }

    public abstract Stage getStage();
}
