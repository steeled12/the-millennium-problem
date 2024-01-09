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
import com.gruppo3.game.model.interactables.*;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.util.Action;

public class CorridoioAtto2Level extends LevelStrategy {

    public CorridoioAtto2Level() {
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


    }

    @Override
    public void init() {
        NPC portiere = new NPC(new Texture(Gdx.files.internal("characters/portiere.png")));
        portiere.getNpcBox().x = 51;
        portiere.getNpcBox().y = 19;
        npcController.npcList.add(portiere);
        
        Dialog portiereDialog = new Dialog();
        LinearDialogNode portiereNode0 = new LinearDialogNode("Buonasera, potrebbe aprire la porta?", 0);
        LinearDialogNode portiereNode1 = new LinearDialogNode("...", 1);
        LinearDialogNode portiereNode2 = new LinearDialogNode("...", 2);
        LinearDialogNode portiereNode3 = new LinearDialogNode("Che lezione hai?", 3);
        LinearDialogNode portiereNode4 = new LinearDialogNode("Nessuna, ma devo incontrare un professore", 4);
        LinearDialogNode portiereNode5 = new LinearDialogNode("...", 5);
        LinearDialogNode portiereNode6 = new LinearDialogNode("...", 6);
        LinearDialogNode portiereNode7 = new LinearDialogNode("Sembra si sia bloccato.\nMeglio cercare un altro modo per entrare", 7);
        portiereNode0.setPointer(1);
        portiereNode1.setPointer(2);
        portiereNode2.setPointer(3);
        portiereNode3.setPointer(4);
        portiereNode4.setPointer(5);
        portiereNode5.setPointer(6);
        portiereNode6.setPointer(7);
        portiereDialog.addNode(portiereNode0);
        portiereDialog.addNode(portiereNode1);
        portiereDialog.addNode(portiereNode2);
        portiereDialog.addNode(portiereNode3);
        portiereDialog.addNode(portiereNode4);
        portiereDialog.addNode(portiereNode5);
        portiereDialog.addNode(portiereNode6);
        portiereDialog.addNode(portiereNode7);
        portiere.setDialog(portiereDialog);

        Player.getPlayer().getPlayerBox().x = 33;
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
