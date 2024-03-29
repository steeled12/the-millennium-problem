package com.gruppo3.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gruppo3.game.model.menus.MainMenu;
import com.gruppo3.game.model.menus.MenuState;

public class MenuController {
    MenuState currentMenu;

    public MenuController() {
        this.currentMenu = new MainMenu(this);
    }

    public MenuController(MenuState menu) {
        this.currentMenu = menu;
    }

    public void changeState(MenuState menu) {
        this.currentMenu = menu;
        // fix viewport dopo back
        currentMenu.getStage().getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    public Stage getStage() {
        return currentMenu.getStage();
    }
}
