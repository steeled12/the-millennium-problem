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
    protected float r, g, b;

    public MenuState(MenuController loader) {
        atlas = new TextureAtlas("flat-earth/skin/flat-earth-ui.atlas");
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"), atlas);
        this.loader = loader;
        this.r = 0.255f;
        this.g = 0.365f;
        this.b = 0.263f;
    }

    public abstract Stage getStage();
}
