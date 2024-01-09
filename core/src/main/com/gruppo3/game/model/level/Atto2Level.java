package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.controller.SettingController;
import com.gruppo3.game.model.interactables.*;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.model.Player;

public class Atto2Level extends LevelStrategy {

    public Atto2Level() {
        super();

        // map
        this.map = new TmxMapLoader().load("map/atto2/Piano_terra.tmx");
        // scaling a game units
        MapLayer collisionObjectLayer = this.map.getLayers().get("Collisioni");
        for (MapObject object : collisionObjectLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                rect.x *= unitScale;
                rect.y *= unitScale;
                rect.width *= unitScale;
                rect.height *= unitScale;
            }
        }
        // render
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        // items
        ScriptableObject portaAula6 = new ScriptableObject(new Rectangle(4, 15, 2, 2), true) {
            @Override
            public void action() {
                changeMap("map/atto2/aula4.tmx", 4, 1);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaAula6);
    }

    @Override
    public void init() {

    }

    public void changeMap(String mapName, float x, float y) {
        this.map = new TmxMapLoader().load(mapName);
        
        MapLayer collisionObjectLayer = this.map.getLayers().get("Collisioni");
        for (MapObject object : collisionObjectLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                rect.x *= unitScale;
                rect.y *= unitScale;
                rect.width *= unitScale;
                rect.height *= unitScale;
            }
        }
        this.renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        Player.getPlayer().getPlayerBox().x = x;
        Player.getPlayer().getPlayerBox().y = y;
        
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
