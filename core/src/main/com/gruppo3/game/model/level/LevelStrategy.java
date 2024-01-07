package com.gruppo3.game.model.level;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gruppo3.game.controller.ItemController;
import com.gruppo3.game.controller.NPCController;

public abstract class LevelStrategy {

    TiledMap map;
    NPCController npcController;
    ItemController itemController;
    OrthogonalTiledMapRenderer renderer;

    public LevelStrategy() {
        this.npcController = new NPCController();
        this.itemController = new ItemController();
    }

    protected final float unitScale = 1 / 16f;

    public abstract void init();

    public abstract TiledMap getMap();

    public abstract NPCController getNpcController();

    public abstract ItemController getItemController();

    public abstract OrthogonalTiledMapRenderer getRenderer();

    public abstract void dispose();

}
