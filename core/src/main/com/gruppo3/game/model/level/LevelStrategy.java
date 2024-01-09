package com.gruppo3.game.model.level;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gruppo3.game.controller.ItemController;
import com.gruppo3.game.controller.NPCController;
import com.gruppo3.game.controller.ScriptableObjectsController;
import com.badlogic.gdx.audio.Music;

public abstract class LevelStrategy {

    TiledMap map;
    NPCController npcController;
    ItemController itemController;
    ScriptableObjectsController scriptableObjectsController;
    OrthogonalTiledMapRenderer renderer;

    public LevelStrategy() {
        this.npcController = new NPCController();
        this.itemController = new ItemController();
        this.scriptableObjectsController = new ScriptableObjectsController();
    }

    protected final float unitScale = 1 / 16f;

    public abstract void init();

    public TiledMap getMap() {
        return this.map;
    }

    public NPCController getNpcController() {
        return this.npcController;
    }

    public ItemController getItemController() {
        return this.itemController;
    }

    public ScriptableObjectsController getScriptableObjectsController() {
        return this.scriptableObjectsController;
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return this.renderer;
    }

    public Music getMusic() {
        return null;
    }

    public abstract void dispose();

}
