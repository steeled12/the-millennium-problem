package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gruppo3.game.controller.ItemController;
import com.gruppo3.game.controller.NPCController;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.Cat;
import com.gruppo3.game.model.interactables.Computer;
import com.gruppo3.game.model.interactables.GenericItem;
import com.gruppo3.game.model.interactables.PickableItem;

public class SecretRoomLevel extends LevelStrategy {

    public static Music music = Gdx.audio.newMusic(Gdx.files.internal("music/CoconutMall.mp3"));

    public SecretRoomLevel() {
        super();

        // map
        map = new TmxMapLoader().load("map/atto3/stanza-segreta.tmx");
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
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
    }

    @Override
    public void init() {
        // item e npc
        Computer computer = new Computer();
        itemController.addwithId(computer, 3);

        Cat npc = new Cat(new Texture("map/atto3/cat.png"));
        npc.getNpcBox().x = 10;
        npc.getNpcBox().y = 10;
        npcController.add(npc);

        // tutti i dialoghi

        GenericItem libreria = new GenericItem("libreria");
        itemController.addwithId(libreria, 4);
        Dialog libDialog = new Dialog();
        LinearDialogNode libNode0 = new LinearDialogNode("Un foglietto sporge leggermente \n dalla libreria", 0);
        ChoiceDialogNode libNode1 = new ChoiceDialogNode("Leggi il foglietto?", 1);
        LinearDialogNode linNode2 = new LinearDialogNode("\"Il vero grande fratello nacque quell\'anno\"", 2);

        libNode0.setPointer(1);
        libNode1.addChoice("Si", 2);
        libNode1.addChoice("No");

        libDialog.addNode(libNode0);
        libDialog.addNode(libNode1);
        libDialog.addNode(linNode2);

        libreria.setDialog(libDialog);

        GenericItem cassetto = new GenericItem("cassetto");
        itemController.addwithId(cassetto, 34);
        Dialog cassDialog = new Dialog();
        LinearDialogNode cassNode0 = new LinearDialogNode("Ci sono delle scritte incise sul cassetto", 0);
        LinearDialogNode cassNode1 = new LinearDialogNode(
                "\"Sono la struttura migliore, ma tutti si \n dimenticano di me\"", 1);

        cassDialog.addNode(cassNode0);
        cassDialog.addNode(cassNode1);

        cassetto.setDialog(cassDialog);

        PickableItem pippo = new PickableItem("caminoPippo", "map/atto3/Living_Room_Singles_111.png");
        pippo.getBox().x = 10;
        pippo.getBox().y = 10;
        pippo.getBox().width = 1;
        pippo.getBox().height = 1;
        itemController.addWithOutId(pippo);

    }

    @Override
    public TiledMap getMap() {
        return this.map;
    }

    @Override
    public NPCController getNpcController() {
        return this.npcController;
    }

    @Override
    public ItemController getItemController() {
        return this.itemController;
    }

    @Override
    public OrthogonalTiledMapRenderer getRenderer() {
        return this.renderer;
    }

    public static void setMusic(Music music) {
        SecretRoomLevel.music = music;
        music.play();
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}