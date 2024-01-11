package com.gruppo3.game.model.level;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.model.interactables.*;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.GameScreen;

public class Aula6Atto1Level extends LevelStrategy {

    public Aula6Atto1Level() {
        super();

        // map
        this.map = new TmxMapLoader().load("map/atto2/aula41.tmx");
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
    }

    @Override
    public void init() {
        // items
        ScriptableObject portaSx = new ScriptableObject(new Rectangle(4, 0, 2, 2), true) {
            @Override
            public void action() {
                GameScreen.levelToLoad = "CorridoioAtto1Level";
                GameScreen.levelController.setLevel(new CorridoioAtto1Level());
                Player.getPlayer().getPlayerBox().x = 4;
                Player.getPlayer().getPlayerBox().y = 14;
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaSx);

        ScriptableObject portaDx = new ScriptableObject(new Rectangle(24, 0, 2, 2), true) {
            @Override
            public void action() {
                GameScreen.levelToLoad = "CorridoioAtto1Level";
                GameScreen.levelController.setLevel(new CorridoioAtto1Level());
                Player.getPlayer().getPlayerBox().x = 12;
                Player.getPlayer().getPlayerBox().y = 14;
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaDx);

        Player.getPlayer().getPlayerBox().x = 25;
        Player.getPlayer().getPlayerBox().y = 1;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
