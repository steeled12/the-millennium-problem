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
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.interactables.*;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.util.Action;
import com.gruppo3.game.screens.GameScreen;
import com.gruppo3.game.controller.SettingController;

public class CorridoioAtto2Level extends LevelStrategy {

    public CorridoioAtto2Level() {
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
        ScriptableObject portaRettore = new ScriptableObject("PortaRettore", new Rectangle(34, 0, 2, 2), new Action() {
            @Override
            public void action() {
                GameScreen.levelToLoad = "StanzaRettore";
                GameScreen.levelController.setLevel(new StanzaRettoreLevel());
            }
        });
        ScriptableObject portaRettangolo = new ScriptableObject("PortaRettangolo", new Rectangle(0, 0, 2, 2), new Action() {
            @Override
            public void action() {
                GameScreen.levelToLoad = "StanzaRettangolo";
                GameScreen.levelController.setLevel(new StanzaRettangolo());
            }
        });

        Player.getPlayer().getPlayerBox().x = 33;
        Player.getPlayer().getPlayerBox().y = 1;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
