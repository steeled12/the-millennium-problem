package com.gruppo3.game.model.level;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.interactables.ScriptableObject;
import com.gruppo3.game.screens.GameScreen;

public class PrimoPianoAtto1Level extends LevelStrategy {
    public PrimoPianoAtto1Level() {
        super();

        // map
        this.map = new TmxMapLoader().load("map/atto1/Primo_piano.tmx");

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
        // scriptable
        ScriptableObject portaStanzaRettangolo = new ScriptableObject(new Rectangle(51, 14, 2, 2), true) {
            @Override
            public void action() {
                GameScreen.levelController.setLevel(new StanzaRettangoloAtto1Level());
            }
        };
        ScriptableObject scale = new ScriptableObject(new Rectangle(37, 20, 3, 2), true) {
            @Override
            public void action() {
                if (GameScreen.savedInformation.get("atto").equals("atto2")) {
                    GameScreen.levelController.setLevel(new CorridoioAtto2Level());
                    Player.getPlayer().getPlayerBox().setPosition(32, 17);
                } else {
                    GameScreen.levelController.setLevel(new CorridoioAtto1Level());
                    Player.getPlayer().getPlayerBox().setPosition(31, 19);
                }

            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaStanzaRettangolo);
        scriptableObjectsController.scriptableObjectsList.add(scale);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
