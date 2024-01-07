package com.gruppo3.game.model.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.model.interactables.Cat;

public class TutorialLevel extends LevelStrategy {

    public TutorialLevel() {
        super();

        // map
        this.map = new TmxMapLoader().load("maps/tutorial/Tutorial_map.tmx");
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
        // NPC
        Cat cat = new Cat(
                new Texture("cat.png"));
        cat.getNpcBox().x = 14;
        cat.getNpcBox().y = 16;
        npcController.add(cat);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
