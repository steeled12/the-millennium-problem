package com.gruppo3.game.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.gruppo3.game.controller.SettingController;
import com.gruppo3.game.model.Player;
import com.gruppo3.game.model.dialog.Dialog;
import com.gruppo3.game.model.dialog.LinearDialogNode;
import com.gruppo3.game.model.interactables.NPC;
import com.gruppo3.game.screens.GameScreen;

public class CreditiLevel extends LevelStrategy {
    public CreditiLevel() {
        super();
        // map
        this.map = new TmxMapLoader().load("map/credits/credits.tmx");

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
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music/credits.mp3"));
        music.setLooping(false);
    }

    @Override
    public void init() {
        // Player
        music.setVolume(0);
        fadeMusic();
        music.play();
        Player.getPlayer().getPlayerBox().x = 8f;
        Player.getPlayer().getPlayerBox().y = 5;

        NPC peppe = new NPC(
                new Texture("characters/peppe.png"));
        peppe.getNpcBox().setPosition(6.5f, 8);
        peppe.setNPCDirection(NPC.Direction.SOUTH);

        NPC andrea = new NPC(
                new Texture("characters/andrea.png"));
        andrea.getNpcBox().setPosition(8, 8);
        andrea.setNPCDirection(NPC.Direction.SOUTH);

        NPC cristina = new NPC(
                new Texture("characters/cristina.png"));
        cristina.getNpcBox().setPosition(9.5f, 8);
        cristina.setNPCDirection(NPC.Direction.SOUTH);

        Dialog dialogoCredits = new Dialog();
        dialogoCredits.addNode(
                new LinearDialogNode("Peppe:\nGrazie per aver giocato al nostro gioco!", 0).setPointer(1));
        dialogoCredits.addNode(
                new LinearDialogNode("Cristina\nSperiamo ti sia piaciuto!", 1).setPointer(2));
        dialogoCredits
                .addNode(new LinearDialogNode(
                        "Andrea:\nLo sai che hai ottenuto il finale {Inserire finale}?",
                        2).setPointer(3));
        dialogoCredits
                .addNode(new LinearDialogNode(
                        "Andrea:\nLo sai che in base agli indizzi trovati e scelte fatte\npuoi ottenere altri finali?\nRigioca per ottenerne di altri!",
                        3).setPointer(4));
        dialogoCredits
                .addNode(new LinearDialogNode(
                        "Peppe:\nIn totale sono 3!",
                        4));

        peppe.setDialog(dialogoCredits);
        cristina.setDialog(dialogoCredits);
        andrea.setDialog(dialogoCredits);

        npcController.add(peppe);
        npcController.add(andrea);
        npcController.add(cristina);

        Dialog dialogoFinale = new Dialog();
        dialogoFinale.addNode(
                new LinearDialogNode("Peppe:\nQuindi è questa la tua storia?", 0).setPointer(1));
        dialogoFinale.addNode(
                new LinearDialogNode("Cristina\nWow!", 1).setPointer(2));
        dialogoFinale
                .addNode(new LinearDialogNode("Andrea:\nPossiamo usarla per il nostro progetto?", 2));

        GameScreen.dialogController.startDialog(dialogoFinale);
        Gdx.app.log("AttoFinaleLevel", "Audio");
    }

    private void fadeMusic() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (music.getVolume() <= SettingController.musicVolume)
                    music.setVolume(music.getVolume() + 0.01f);
                else {
                    this.cancel();
                }
            }
        }, 1f, 0.1f);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
