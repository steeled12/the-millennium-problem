package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.*;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.GameScreen;

public class Aula4Atto1Level extends LevelStrategy {

    public Aula4Atto1Level() {
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
                GameScreen.levelToLoad = "CorridoioAtto2Level";
                GameScreen.levelController.setLevel(new CorridoioAtto2Level());
                Player.getPlayer().getPlayerBox().x = 58;
                Player.getPlayer().getPlayerBox().y = 14;
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaSx);

        ScriptableObject portaDx = new ScriptableObject(new Rectangle(24, 0, 2, 2), true) {
            @Override
            public void action() {
                GameScreen.levelToLoad = "CorridoioAtto2Level";
                GameScreen.levelController.setLevel(new CorridoioAtto2Level());
                Player.getPlayer().getPlayerBox().x = 64;
                Player.getPlayer().getPlayerBox().y = 14;
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaDx);

        NPC peppe = new NPC(new Texture(Gdx.files.internal("characters/peppe.png")));
        peppe.getNpcBox().x = 1;
        peppe.getNpcBox().y = 1;
        npcController.add(peppe);

        Dialog peppeDialog = new Dialog();
        LinearDialogNode peppeNode0 = new LinearDialogNode("Peppe:\nDi nuovo qui? Vuoi sapere qualcosa?", 0);
        LinearDialogNode peppeNode1 = new LinearDialogNode("Di cosa parla il vostro gioco?", 1);
        LinearDialogNode peppeNode2 = new LinearDialogNode(
                "Cristina:\nAncora non lo sappiamo in realtà...\nHai qualche idea?", 2);
        LinearDialogNode peppeNode3 = new LinearDialogNode("Ehm no, per ora ho la testa ad altro", 3);
        LinearDialogNode peppeNode4 = new LinearDialogNode(
                "Andrea:\nPerché non torni a raccontarci qualcosa\n quando avrai finito di correre in giro?", 4);

        peppeNode0.setPointer(1);
        peppeNode1.setPointer(2);
        peppeNode2.setPointer(3);
        peppeNode3.setPointer(4);

        peppeDialog.addNode(peppeNode0);
        peppeDialog.addNode(peppeNode1);
        peppeDialog.addNode(peppeNode2);
        peppeDialog.addNode(peppeNode3);
        peppeDialog.addNode(peppeNode4);
        peppe.setDialog(peppeDialog);

        NPC cristina = new NPC(new Texture(Gdx.files.internal("characters/cristina.png")));
        cristina.getNpcBox().x = 1;
        cristina.getNpcBox().y = 2;
        npcController.add(cristina);
        cristina.setDialog(peppeDialog);

        NPC andrea = new NPC(new Texture(Gdx.files.internal("characters/andrea.png")));
        andrea.getNpcBox().x = 2;
        andrea.getNpcBox().y = 2;
        npcController.add(andrea);
        andrea.setDialog(peppeDialog);

        Player.getPlayer().getPlayerBox().x = 25;
        Player.getPlayer().getPlayerBox().y = 1;
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
