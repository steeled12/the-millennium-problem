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
import com.gruppo3.game.MyGame;
import com.gruppo3.game.controller.SettingController;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.dialog.ChoiceDialogNode;
import com.gruppo3.game.model.interactables.*;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.screens.TransitionScreen;
import com.gruppo3.game.util.Action;
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
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(), new CorridoioAtto1Level(), (MyGame) Gdx.app.getApplicationListener(), 4, 14);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaSx);

        ScriptableObject portaDx = new ScriptableObject(new Rectangle(24, 0, 2, 2), true) {
            @Override
            public void action() {
                TransitionScreen fadeScreen = new TransitionScreen(GameScreen.levelController.getCurrentLevel(), new CorridoioAtto1Level(), (MyGame) Gdx.app.getApplicationListener(), 12, 14);
                ((MyGame) Gdx.app.getApplicationListener()).setScreen(fadeScreen);
            }
        };
        scriptableObjectsController.scriptableObjectsList.add(portaDx);

        NPC prof = new NPC(new Texture(Gdx.files.internal("characters/studente1.png")));
        prof.getNpcBox().x = 1;
        prof.getNpcBox().y = 1;
        prof.setNPCDirection(NPC.Direction.NORTH);
        npcController.add(prof);

        Dialog profDialog = new Dialog();
        LinearDialogNode profNode0 = new LinearDialogNode("Buonasera professore, posso disturbarla?", 0);
        LinearDialogNode profNode1 = new LinearDialogNode("Valenti:\nPerdonami, ma sono impegnato", 1);
        ChoiceDialogNode profNode2 = new ChoiceDialogNode("...", 2);
        LinearDialogNode profNode3 = new LinearDialogNode("ATARI È MEGLIO DI COMMODORE", 3, new Action() {
            @Override
            public void action() {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-badum.mp3"));
                sound.play(SettingController.gameVolume);
            }
        });
        LinearDialogNode profNode4 = new LinearDialogNode("Valenti:\nCOSA HAI DETTO?", 4);
        LinearDialogNode profNode5 = new LinearDialogNode(
                "Scusi, volevo solo chiederle se può chiedere al portiere\ndi aprire la porta delle scale", 5);
        LinearDialogNode profNode6 = new LinearDialogNode("Valenti:\n...", 6);
        LinearDialogNode profNode7 = new LinearDialogNode("Valenti:\nVa bene... basta che non ti fai più vedere...", 7);
        LinearDialogNode profNode8 = new LinearDialogNode("Grazie professore", 8);
        LinearDialogNode profNode9 = new LinearDialogNode("[Adesso la porta dovrebbe essere aperta]", 9, new Action() {
            @Override
            public void action() {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sfx-selectjingle.mp3"));
                sound.play(SettingController.gameVolume);
                GameScreen.savedInformation.put("portaScale", "true");
            }
        });

        profNode0.setPointer(1);
        profNode1.setPointer(2);
        profNode2.addChoice("Vai via");
        profNode2.addChoice("Fallo innervosire", 3);
        profNode3.setPointer(4);
        profNode4.setPointer(5);
        profNode5.setPointer(6);
        profNode6.setPointer(7);
        profNode7.setPointer(8);
        profNode8.setPointer(9);

        profDialog.addNode(profNode0);
        profDialog.addNode(profNode1);
        profDialog.addNode(profNode2);
        profDialog.addNode(profNode3);
        profDialog.addNode(profNode4);
        profDialog.addNode(profNode5);
        profDialog.addNode(profNode6);
        profDialog.addNode(profNode7);
        profDialog.addNode(profNode8);
        profDialog.addNode(profNode9);

        prof.setDialog(profDialog);

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
