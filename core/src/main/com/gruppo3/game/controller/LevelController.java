package com.gruppo3.game.controller;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gruppo3.game.model.level.LevelStrategy;

public class LevelController {
    LevelStrategy currentLevel;

    public LevelController(LevelStrategy strategy) {
        this.currentLevel = strategy;
    }

    public void setLevel(LevelStrategy level) {
        this.currentLevel = level;
    }

    public void init() {
        this.currentLevel.init();
    }

    public TiledMap getMap() {
        return currentLevel.getMap();
    }

    public NPCController getNpcController() {
        return currentLevel.getNpcController();
    }

    public ItemController getItemController() {
        return currentLevel.getItemController();
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return currentLevel.getRenderer();
    }

    public void dispose() {
        currentLevel.dispose();
    }
}
